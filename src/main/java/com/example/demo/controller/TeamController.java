package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Player;
import com.example.demo.model.Team;
import com.example.demo.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import javax.validation.Valid;

@RestController
public class TeamController {

    @Autowired
    private TeamRepository teamRepository;
    //list all teams
    @GetMapping("/teams")
    public Page<Team> getAllTeams(Pageable pageable) {
        return teamRepository.findAll(pageable);
    }
 
    // create a new team
    @PostMapping("/teams")
    public Team createTeam(@Valid @RequestBody Team team) {
        return teamRepository.save(team);
    }
    // update a team
    @PutMapping("/teams/{teamId}")
    public Team updateTeam(@PathVariable Long teamId, @Valid @RequestBody Team teamRequest) {
        return teamRepository.findById(teamId).map(team -> {
            team.setName(teamRequest.getName());
            team.setLocation(teamRequest.getLocation());
            return teamRepository.save(team);
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + teamId + " not found"));
    }

    // delete a team with team id
    @DeleteMapping("/teams/{teamId}")
    public ResponseEntity<?> deletePost(@PathVariable Long teamId) {
        return teamRepository.findById(teamId).map(team -> {
            teamRepository.delete(team);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("PostId " + teamId + " not found"));
    }

}
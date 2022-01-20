package com.example.demo.controller;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Player;
import com.example.demo.model.Team;
import com.example.demo.repository.PlayerRepository;
import com.example.demo.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class PlayerController {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;
    //this players mapping show all players
    @GetMapping("/players")
    public Page<Player> getAllPlayers(Pageable pageable) {
        return playerRepository.findAll(pageable);
    }
     // this will show all players by team id
    @GetMapping("/teams/{teamId}/players")
    public Page<Player> getAllPlayersByPostId(@PathVariable (value = "teamId") Long teamId,
                                                Pageable pageable) {
        return playerRepository.findByTeamId(teamId, pageable);
    }
    // create players
    @PostMapping("/teams/{teamId}/players")
    public Player createPlayer(@PathVariable (value = "teamId") Long teamId,
                                 @Valid @RequestBody Player player) {
        return teamRepository.findById(teamId).map(team -> {
            player.setTeam(team);
            return playerRepository.save(player);
        }).orElseThrow(() -> new ResourceNotFoundException("TeamId " + teamId + " not found"));
    }
    // update players
    @PutMapping("/teams/{teamId}/players/{playerId}")
    public Player updatePlayer(@PathVariable (value = "teamId") Long teamId,
                                 @PathVariable (value = "playerId") Long playerId,
                                 @Valid @RequestBody Player playerRequest) {
        if(!teamRepository.existsById(teamId)) {
            throw new ResourceNotFoundException("TeamId " + teamId + " not found");
        }

        return playerRepository.findById(playerId).map(player -> {
            player.setName(playerRequest.getName());
            return playerRepository.save(player);
        }).orElseThrow(() -> new ResourceNotFoundException("PlayerId " + playerId + "not found"));
    }
    // delete players
    @DeleteMapping("/teams/{teamId}/players/{playerId}")
    public ResponseEntity<?> deleteComment(@PathVariable (value = "teamId") Long teamId,
                              @PathVariable (value = "playerId") Long playerId) {
        return playerRepository.findByIdAndTeamId(playerId, teamId).map(player -> {
            playerRepository.delete(player);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("player not found with id " + playerId + " and playerId " + teamId));
    }
}
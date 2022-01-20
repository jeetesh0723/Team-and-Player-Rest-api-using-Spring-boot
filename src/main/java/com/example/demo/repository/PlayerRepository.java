package com.example.demo.repository;

import com.example.demo.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Page<Player> findByTeamId(Long teamId, Pageable pageable);
    Optional<Player> findByIdAndTeamId(Long id, Long teamId);
}
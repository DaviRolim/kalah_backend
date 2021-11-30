package com.github.kalaha.kalaha_game.repository;

import com.github.kalaha.kalaha_game.repository.entities.GameState;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GameStateRepository extends JpaRepository<GameState, Integer> {

}

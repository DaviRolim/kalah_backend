package com.github.kalaha.kalaha_game.repository;
import com.github.kalaha.kalaha_game.domain.entities.KalahBoard;

import org.springframework.data.jpa.repository.JpaRepository;


public interface KalahBoardRepository extends JpaRepository<KalahBoard, Integer> {
    
}

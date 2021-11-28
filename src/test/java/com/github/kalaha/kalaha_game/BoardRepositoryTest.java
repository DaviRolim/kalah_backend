package com.github.kalaha.kalaha_game;

import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.github.kalaha.kalaha_game.domain.Board;
import com.github.kalaha.kalaha_game.domain.BoardRepository;
import com.github.kalaha.kalaha_game.repository.KalahBoardRepository;
import com.github.kalaha.kalaha_game.repository.entities.KalahBoard;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BoardRepositoryTest {
    @Autowired
    BoardRepository boardRepository;

    @Resource
    private KalahBoardRepository kalahBoardRepository;

    @BeforeEach
    @Transactional
    void saveItemInRepository() {
        String pits = "4,0,5,5,5,5,0,4,4,4,4,4,4,0";
        KalahBoard kalahBoard = new KalahBoard(pits);
        kalahBoardRepository.save(kalahBoard);
    }

    @Test
    @Transactional
    void shouldReturnABoardWhenGameIdExists() {
        assert (boardRepository.getByGameId(1) instanceof Board);

    }

    @Test
    @Transactional
    void shouldThrowExceptionWhenIdNotInDatabase() {
        assertThrows(EntityNotFoundException.class, () -> {
            boardRepository.getByGameId(999);
        });
    }

}

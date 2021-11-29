package com.github.kalaha.kalaha_game;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.github.kalaha.kalaha_game.domain.Board;
import com.github.kalaha.kalaha_game.domain.BoardRepository;
import com.github.kalaha.kalaha_game.repository.KalahBoardRepository;
import com.github.kalaha.kalaha_game.repository.entities.KalahBoard;
import com.github.kalaha.kalaha_game.utils.failures.Failure;
import com.github.kalaha.kalaha_game.utils.failures.GameNotFoundFailure;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.vavr.control.Either;

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
        kalahBoard.setId(1);
        kalahBoardRepository.save(kalahBoard);
    }

    @Test
    @Transactional
    void shouldReturnLeftWithFailureWhenIdNotInDatabase() {
        Either<Failure, Board> result = boardRepository.getByGameId(999);
        assertTrue(result.isLeft());
    }

    @Test
    @Transactional
    void shouldReturnRightWithBoardWhenGameIdExists() {
        // BeforeEach will insert the secondGame
        Either<Failure, Board> result = boardRepository.getByGameId(2);
        assertTrue(result.isRight());
        assert (result.get() instanceof Board);
    }

}

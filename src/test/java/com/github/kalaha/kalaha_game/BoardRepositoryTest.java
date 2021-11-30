package com.github.kalaha.kalaha_game;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import com.github.kalaha.kalaha_game.domain.Board;
import com.github.kalaha.kalaha_game.domain.BoardRepository;
import com.github.kalaha.kalaha_game.repository.BoardRepositoryImpl;
import com.github.kalaha.kalaha_game.repository.GameStateRepository;
import com.github.kalaha.kalaha_game.repository.entities.GameState;
import com.github.kalaha.kalaha_game.utils.failures.Failure;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.vavr.control.Either;

@SpringBootTest
class BoardRepositoryTest {
    // @Autowired
    // BoardRepository boardRepository;

    // @Resource
    // private gameStateRepository gameStateRepository;

    @Mock
    private GameStateRepository mockedKalahrepository;

    @InjectMocks
    private BoardRepositoryImpl boardRepository;

    @BeforeEach
    void saveItemInRepository() {
        String pits = "4,0,5,5,5,5,0,4,4,4,4,4,4,0";
        GameState gameState = new GameState(pits);
        gameState.setId(1);
        when(mockedKalahrepository.save(gameState)).thenReturn(gameState);
        when(mockedKalahrepository.getById(1)).thenReturn(gameState);
        when(mockedKalahrepository.getById(999)).thenThrow(new EntityNotFoundException());
    }

    @Test
    void shouldReturnLeftWithFailureWhenIdNotInDatabase() {
        Either<Failure, Board> result = boardRepository.getByGameId(999);
        assertTrue(result.isLeft());
    }

    @Test
    void shouldReturnRightWithBoardWhenGameIdExists() {
        Either<Failure, Board> result = boardRepository.getByGameId(1);
        assertTrue(result.isRight());
        assert (result.get() instanceof Board);
    }

}

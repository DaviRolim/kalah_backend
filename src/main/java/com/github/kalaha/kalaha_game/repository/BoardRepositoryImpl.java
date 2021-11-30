package com.github.kalaha.kalaha_game.repository;

import com.github.kalaha.kalaha_game.domain.Board;
import com.github.kalaha.kalaha_game.domain.BoardRepository;
import com.github.kalaha.kalaha_game.repository.entities.GameState;
import com.github.kalaha.kalaha_game.utils.failures.Failure;
import com.github.kalaha.kalaha_game.utils.failures.GameNotFoundFailure;

import org.springframework.stereotype.Component;

import io.vavr.control.Either;

@Component
public class BoardRepositoryImpl implements BoardRepository {

    private GameStateRepository gameStateRepository;

    public BoardRepositoryImpl(GameStateRepository gameStateRepository) {
        this.gameStateRepository = gameStateRepository;
    }

    @Override
    public Either<Failure, Board> getByGameId(int id) {
        try {
            GameState gameState = gameStateRepository.getById(id);
            return Either.right(mapgameStateToBoard(gameState));
        } catch (Exception e) {
            return Either.left(new GameNotFoundFailure("Problem retrieving board for id " + id));
        }
    }

    @Override
    public int saveGameStatusAndGetId(Board board, int gameId) {
        GameState gameState;
        if (gameId == 0) {
            gameState = new GameState(mapArrayOfIntToString(board.getPits()));
        } else {
            gameState = gameStateRepository.getById(gameId);
            gameState.setPits(mapArrayOfIntToString(board.getPits()));
            gameState.setPlayerTurn(board.getCurrentPlayer().ordinal());
        }
        gameStateRepository.save(gameState);
        return gameState.getId();

    }

    private int[] mapPitStringToArrayOfInt(String pitString) {
        String[] pits = pitString.split(",");
        int[] intPits = new int[pits.length];
        for (int i = 0; i < intPits.length; i++) {
            intPits[i] = Integer.parseInt(pits[i]);
        }
        return intPits;
    }

    private String mapArrayOfIntToString(int[] pits) {
        String sPits = "";
        for (int i = 0; i < pits.length; i++) {
            if (i == 0) {
                sPits += String.valueOf(pits[i]);
            } else {
                sPits += "," + String.valueOf(pits[i]);
            }
        }
        return sPits;
    }

    private Board mapgameStateToBoard(GameState gameState) {
        int id = gameState.getId();
        int[] pits = mapPitStringToArrayOfInt(gameState.getPits());
        int player = gameState.getPlayerTurn();
        Board board = new Board();
        board.setPits(pits);
        board.setCurrentPlayer(player);
        board.setGameId(id);
        return board;
    }

}

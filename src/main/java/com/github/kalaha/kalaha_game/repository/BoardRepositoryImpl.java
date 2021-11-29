package com.github.kalaha.kalaha_game.repository;

import com.github.kalaha.kalaha_game.domain.Board;
import com.github.kalaha.kalaha_game.domain.BoardRepository;
import com.github.kalaha.kalaha_game.repository.entities.KalahBoard;
import com.github.kalaha.kalaha_game.utils.failures.Failure;
import com.github.kalaha.kalaha_game.utils.failures.GameNotFoundFailure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.vavr.control.Either;

@Component
public class BoardRepositoryImpl implements BoardRepository {

    @Autowired
    private KalahBoardRepository kalahBoardRepository;

    @Override
    public Either<Failure, Board> getByGameId(int id) {
        try {
            KalahBoard kalahBoard = kalahBoardRepository.getById(id);
            return Either.right(mapKalahBoardToBoard(kalahBoard));
        } catch (Exception e) {
            return Either.left(new GameNotFoundFailure("Problem retrieving board for id " + id));
        }
    }

    @Override
    public int saveGameStatusAndGetId(Board board, int gameId) {
        KalahBoard kalahBoard;
        if (gameId == 0) {
            kalahBoard = new KalahBoard(mapArrayOfIntToString(board.getPits()));
        } else {
            kalahBoard = kalahBoardRepository.getById(gameId);
            kalahBoard.setPits(mapArrayOfIntToString(board.getPits()));
            kalahBoard.setPlayerTurn(board.getCurrentPlayer().ordinal());
        }
        kalahBoardRepository.save(kalahBoard);
        return kalahBoard.getId();

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

    private Board mapKalahBoardToBoard(KalahBoard kalahBoard) {
        int id = kalahBoard.getId();
        int[] pits = mapPitStringToArrayOfInt(kalahBoard.getPits());
        int player = kalahBoard.getPlayerTurn();
        Board board = new Board();
        board.setPits(pits);
        board.setCurrentPlayer(player);
        board.setGameId(id);
        return board;
    }

}

package com.github.kalaha.kalaha_game.domain;

import com.github.kalaha.kalaha_game.utils.failures.Failure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.vavr.control.Either;

@Component
public class GameOrchestrator {

    @Autowired
    BoardRepository boardRepository;

    public Board startGame() {
        Board board = new Board();
        board.initializePits();
        int id = boardRepository.saveGameStatusAndGetId(board, 0);
        board.setGameId(id);
        return board;
    }

    public Either<Failure, Board> makePlay(int index, int gameId) {
        Either<Failure, Board> result = boardRepository.getByGameId(gameId);
        if (result.isRight()) {
            Board board = result.get();
            board.executeMove(index);
            boardRepository.saveGameStatusAndGetId(board, gameId);
            return Either.right(board);
        } else {
            return Either.left(result.getLeft());
        }
    }

}

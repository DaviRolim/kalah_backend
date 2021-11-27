package com.github.kalaha.kalaha_game.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Game {

    @Autowired BoardRepository boardRepository;

    public Board startGame() {
        Board board = new Board();
        board.initializePits();
        int id = boardRepository.saveGameStatusAndGetId(board, 0);
        board.setGameId(id);
        return board;
    }

    public Board makePlay(int index, int gameId) {
        Board board = boardRepository.getByGameId(gameId);
        board.executeMove(index);
        boardRepository.saveGameStatusAndGetId(board, gameId);
        return board;
    }

    public boolean isGameOver() {
        // return board.isGameOver();
        return false;
    }

    public String getWinningMessage() {
        // if(board.getPlayer1Score() > board.getPlayer2Score()) {
        //     return "Congratulations! Player 1 has won!";
        // } else {
        //     return "Congratulations! Player 2 has won!";
        // }
        return "TODO";
    }


}

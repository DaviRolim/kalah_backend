package com.github.kalaha.kalaha_game.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Game {
    @Autowired
    public Board board;

    public int[] startGame() {
        board.initialize();
        return board.getPits();
    }

    public void makePlay(int index) {
        board.executeMove(index);
    }

    public boolean isGameOver() {
        return board.isGameOver();
    }

    public String getWinningMessage() {
        if(board.getPlayer1Score() > board.getPlayer2Score()) {
            return "Congratulations! Player 1 has won!";
        } else {
            return "Congratulations! Player 2 has won!";
        }
    }


}

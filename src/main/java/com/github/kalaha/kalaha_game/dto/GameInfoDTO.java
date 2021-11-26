package com.github.kalaha.kalaha_game.dto;


public class GameInfoDTO {

    public int[] boardState;
    public String currentPlayer;
    public int player1Score;
    public int player2Score;
    public String message;
    

    public GameInfoDTO(int[] boardState, String currentPlayer, int player1Score, int player2Score, String message) {
        this.boardState = boardState;
        this.currentPlayer = currentPlayer;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.message = message;
    }
}

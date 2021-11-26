package com.github.kalaha.kalaha_game.domain;

public interface Board {

    public void initialize();
    
    public int[] getPits();
    
    public void executeMove(int index);

    public int moveUnits(int index);

    public boolean isGameOver();

    public int getPlayer1Score();

    public int getPlayer2Score();

    public String getCurrentPlayerAsString();

    public boolean shouldChangeCurrentPlayer(int lastPitIndex);
}



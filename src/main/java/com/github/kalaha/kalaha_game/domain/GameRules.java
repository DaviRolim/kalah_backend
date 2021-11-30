package com.github.kalaha.kalaha_game.domain;

public interface GameRules {

    public boolean shouldChangeCurrentPlayer(int lastPitIndex);

    public boolean canCaptureOppositeSide(int lastIndex);

    public void executeCapture(int lastPitIndex);

    public void storeRemainingUnits();

}

package com.github.kalaha.kalaha_game.domain;

public interface BoardRepository {

    public int[] getPitsByGameId(int id);

    public void saveGameStatus(Board board);
}

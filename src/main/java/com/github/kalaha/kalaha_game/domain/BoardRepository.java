package com.github.kalaha.kalaha_game.domain;

public interface BoardRepository {

    public Board getByGameId(int id);

    public int saveGameStatusAndGetId(Board board, int gameId);
}

package com.github.kalaha.kalaha_game.domain;

import com.github.kalaha.kalaha_game.utils.failures.Failure;

import io.vavr.control.Either;

public interface BoardRepository {

    public Either<Failure, Board> getByGameId(int id);

    public int saveGameStatusAndGetId(Board board, int gameId);
}

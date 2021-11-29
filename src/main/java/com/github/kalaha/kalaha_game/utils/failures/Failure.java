package com.github.kalaha.kalaha_game.utils.failures;

public abstract class Failure {
    private String message;

    public Failure(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

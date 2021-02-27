package com.fenrir.ubot.exception;

public class ImageException extends Exception {

    private final String message;

    public ImageException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

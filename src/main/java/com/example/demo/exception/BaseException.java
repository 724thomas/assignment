package com.example.demo.exception;

import lombok.Getter;

@Getter
public abstract class BaseException extends RuntimeException {

    private final int code;
    private final String message;

    public BaseException(String message) {
        code = 0;
        this.message = message;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public String getMessage() {
        return message;
    }
}

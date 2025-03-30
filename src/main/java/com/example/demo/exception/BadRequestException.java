package com.example.demo.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestException extends BaseException {

    public BadRequestException(String message) {
        super(message);
    }
}

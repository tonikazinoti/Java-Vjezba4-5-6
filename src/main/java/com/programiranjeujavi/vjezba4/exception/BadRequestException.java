package com.programiranjeujavi.vjezba4.exception;

public class BadRequestException extends Exception {
    public BadRequestException() {}

    public BadRequestException(String message)
    {
        super(message);
    }
}

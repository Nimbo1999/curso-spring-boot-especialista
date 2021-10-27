package io.github.nimbo1999.exception;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException() {
        super("Incorrect password!");
    }
}

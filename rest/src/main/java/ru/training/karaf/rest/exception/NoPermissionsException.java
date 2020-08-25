package ru.training.karaf.rest.exception;

public class NoPermissionsException extends RuntimeException {
    public NoPermissionsException(String message) {
        super(message);
    }
}

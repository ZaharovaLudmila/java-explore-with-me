package ru.practicum.ewmServer.exception;
public class NotFoundException extends IllegalStateException {

    public NotFoundException(String message) {
        super(message);
    }
}

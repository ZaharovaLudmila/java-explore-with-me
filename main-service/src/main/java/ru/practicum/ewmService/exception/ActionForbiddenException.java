package ru.practicum.ewmService.exception;

public class ActionForbiddenException extends RuntimeException {

    public ActionForbiddenException(String message) {
        super(message);
    }
}

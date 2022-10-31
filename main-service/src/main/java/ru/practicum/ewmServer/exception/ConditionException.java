package ru.practicum.ewmServer.exception;

public class ConditionException extends IllegalArgumentException {
    public ConditionException(String message) {
        super(message);
    }
}

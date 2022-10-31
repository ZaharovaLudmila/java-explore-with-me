package ru.practicum.ewmServer.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

@RestControllerAdvice
public class ErrorHandler {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFoundException(final NotFoundException e) {
        return new ApiError(null, e.getMessage(),"The required object was not found.",
                HttpStatus.NOT_FOUND.toString(), LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleConstraintViolationException(final ConstraintViolationException e) {
        return new ApiError(Arrays.asList(e.getStackTrace().toString()), e.getMessage(),
                "Integrity constraint has been violated",
                HttpStatus.CONFLICT.toString(), LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler({DataIntegrityViolationException.class, ConditionException.class,
    TimeException.class, EventSortException.class, ParticipationRequestException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleConditionException(final Exception e) {
        return new ApiError(Arrays.asList(e.getStackTrace().toString()), e.getMessage(),
                "For the requested operation the conditions are not met.",
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiError handleConstraintViolationException(final Throwable e) {
        return new ApiError(Arrays.asList(e.getStackTrace().toString()), e.getMessage(),
                "Error occurred",
                HttpStatus.INTERNAL_SERVER_ERROR.toString(), LocalDateTime.now().format(formatter));
    }
}

package ru.practicum.ewmService.parametrs;

import java.time.format.DateTimeFormatter;

public class UtilDateFormatter {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static DateTimeFormatter getFormatter() {
        return FORMATTER;
    }
}

package ru.practicum.ewmServer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class AdminUpdateEventRequest {

    private String annotation;
    private Long category;
    private String description;
    private String eventDate;
    private LocationDto location;
    private Boolean paid;
    private int participantLimit;
    private Boolean requestModeration;
    private String title;
}

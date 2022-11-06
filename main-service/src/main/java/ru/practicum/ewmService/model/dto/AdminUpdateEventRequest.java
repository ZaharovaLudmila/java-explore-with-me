package ru.practicum.ewmService.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
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

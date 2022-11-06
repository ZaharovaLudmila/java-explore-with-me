package ru.practicum.ewmService.model.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {

    @Size(max = 1000)
    private String annotation;
    private Long category;
    @Size(max = 5000)
    private String description;
    @Size(max = 50)
    private String eventDate;
    @NotNull
    private Long eventId;
    private Boolean paid;
    private int participantLimit;
    @Size(max = 150)
    private String title;
}

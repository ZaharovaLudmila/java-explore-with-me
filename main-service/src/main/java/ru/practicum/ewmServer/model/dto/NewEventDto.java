package ru.practicum.ewmServer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {

    @NotBlank
    private String annotation;
    @NotNull
    private Long category;
    @NotBlank
    private String description;
    @NotBlank
    private String eventDate;
    @NotNull
    private LocationDto location;
    private Boolean paid;
    private int participantLimit;
    private Boolean requestModeration;
    @NotBlank
    private String title;
}

package ru.practicum.ewm.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewEventDto {

    @NotBlank
    @Size(max = 1000)
    private String annotation;
    @NotNull
    private Long category;
    @NotBlank
    @Size(max = 5000)
    private String description;
    @NotBlank
    @Size(max = 30)
    private String eventDate;
    @NotNull
    private LocationDto location;
    private Boolean paid;
    private int participantLimit;
    private Boolean requestModeration;
    @NotBlank
    @Size(max = 150)
    private String title;
}

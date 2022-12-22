package ru.practicum.ewmService.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ru.practicum.ewmService.model.EventState;
import ru.practicum.ewmService.model.Location;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {

    @NotBlank
    private String annotation;
    @NotBlank
    private CategoryDto category;
    private int confirmedRequests;
    private String createdOn;
    private String description;
    @NotBlank
    private String eventDate;
    private Long id;
    @NotBlank
    private UserShortDto initiator;
    @NotBlank
    private Location location;
    @NotBlank
    private Boolean paid;
    private int participantLimit;
    private String publishedOn;
    private Boolean requestModeration;
    private EventState state;
    @NotBlank
    private String title;
    private int views;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String rate;
}

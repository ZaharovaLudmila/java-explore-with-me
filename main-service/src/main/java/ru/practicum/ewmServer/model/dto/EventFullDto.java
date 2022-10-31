package ru.practicum.ewmServer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmServer.model.EventState;
import ru.practicum.ewmServer.model.Location;

import javax.validation.constraints.NotBlank;

@Data
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
}

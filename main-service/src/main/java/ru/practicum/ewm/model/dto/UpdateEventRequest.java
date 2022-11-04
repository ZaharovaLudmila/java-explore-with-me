package ru.practicum.ewm.model.dto;

import lombok.*;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequest {

    @Size(max = 1000)
    @Nullable
    private String annotation;
    private Long category;
    @Size(max = 5000)
    @Nullable
    private String description;
    @Size(max = 50)
    @Nullable
    private String eventDate;
    @NotNull
    private Long eventId;
    private Boolean paid;
    private int participantLimit;
    @Size(max = 150)
    @Nullable
    private String title;
}

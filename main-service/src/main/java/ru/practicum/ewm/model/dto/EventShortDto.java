package ru.practicum.ewm.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EventShortDto {

    @NotBlank
    private String annotation;
    @NotBlank
    private CategoryDto category;
    private int confirmedRequests;
    @NotBlank
    private String eventDate;
    private Long id;
    @NotBlank
    private UserShortDto initiator;
    @NotBlank
    private Boolean paid;
    @NotBlank
    private String title;
    private int views;
}

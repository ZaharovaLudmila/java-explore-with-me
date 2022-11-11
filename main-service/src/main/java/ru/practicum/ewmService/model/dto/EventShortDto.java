package ru.practicum.ewmService.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String rate;
}

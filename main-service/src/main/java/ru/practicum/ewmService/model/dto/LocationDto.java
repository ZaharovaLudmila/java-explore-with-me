package ru.practicum.ewmService.model.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    private double lat;
    private double lon;
}

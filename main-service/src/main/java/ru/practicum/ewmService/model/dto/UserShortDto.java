package ru.practicum.ewmService.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserShortDto {

    @NotBlank
    private Long id;
    @NotBlank
    private String name;
}

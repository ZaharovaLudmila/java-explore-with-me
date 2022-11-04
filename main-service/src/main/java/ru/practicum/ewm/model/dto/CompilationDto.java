package ru.practicum.ewm.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CompilationDto {

    private List<EventShortDto> events;
    @NotBlank
    private Long id;
    @NotBlank
    private Boolean pinned;
    @NotBlank
    private String title;
}

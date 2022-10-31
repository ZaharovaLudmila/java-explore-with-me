package ru.practicum.ewmServer.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
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

package ru.practicum.ewmService.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewCompilationDto {

    private List<Long> events;
    @NotNull
    private Boolean pinned;
    @NotBlank
    @Size(max = 300)
    private String title;
}

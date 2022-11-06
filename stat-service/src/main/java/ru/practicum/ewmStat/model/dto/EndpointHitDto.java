package ru.practicum.ewmStat.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class EndpointHitDto {
    private Long id;
    @Size(max = 300)
    @NotBlank
    private String app;
    @Size(max = 320)
    @NotBlank
    private String uri;
    @Size(max = 50)
    @NotBlank
    private String ip;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;
}

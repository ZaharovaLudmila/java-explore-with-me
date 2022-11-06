package ru.practicum.ewmService.model.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class NewUserRequest {

    @NotBlank
    @Size(max = 300)
    private String name;
    @NotBlank
    @Email
    @Size(max = 320)
    private String email;
}

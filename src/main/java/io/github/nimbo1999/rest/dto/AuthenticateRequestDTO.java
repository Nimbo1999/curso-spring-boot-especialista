package io.github.nimbo1999.rest.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticateRequestDTO {
    @NotEmpty
    private String login;
    @NotEmpty
    private String senha;
}

package io.github.nimbo1999.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticateResponseDTO {
    private String accessToken;
    private String tokenType;
    private Long expiresIn;
}

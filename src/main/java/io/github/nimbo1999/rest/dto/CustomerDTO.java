package io.github.nimbo1999.rest.dto;

import java.time.Instant;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {
    private Integer id;
    @NotEmpty(message = "Customer name it's Required!")
    private String name;
    private String personId;
    private Instant createdAt;
    private Instant updatedAt;
}

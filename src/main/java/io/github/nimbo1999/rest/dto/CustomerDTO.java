package io.github.nimbo1999.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {
    private Integer id;
    private String name;
    private String personId;
}

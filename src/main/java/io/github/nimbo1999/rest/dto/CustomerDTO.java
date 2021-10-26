package io.github.nimbo1999.rest.dto;

import java.time.Instant;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.br.CPF;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerDTO {
    private Integer id;
    @NotEmpty(message = "{campo.nome.obrigatorio}")
    private String name;
    @NotEmpty(message = "{campo.cpf.obrigatorio}")
    @CPF(message = "{campo.cpf.invalido}")
    private String personId;
    private Instant createdAt;
    private Instant updatedAt;
}

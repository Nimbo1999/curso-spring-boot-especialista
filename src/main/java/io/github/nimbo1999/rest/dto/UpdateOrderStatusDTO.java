package io.github.nimbo1999.rest.dto;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusDTO {
    @NotEmpty(message = "É obrigatório informar o novo status do pedido")
    private String status;
}

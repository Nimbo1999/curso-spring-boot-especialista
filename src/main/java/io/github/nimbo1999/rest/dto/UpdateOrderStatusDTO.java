package io.github.nimbo1999.rest.dto;

import javax.validation.constraints.NotEmpty;

import io.github.nimbo1999.validation.OrderStatusField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateOrderStatusDTO {
    @NotEmpty(message = "É obrigatório informar o novo status do pedido")
    @OrderStatusField
    private String status;
}

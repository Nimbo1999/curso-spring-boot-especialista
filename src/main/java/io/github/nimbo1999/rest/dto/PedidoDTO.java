package io.github.nimbo1999.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.github.nimbo1999.validation.NotEmptyList;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    @NotNull(message = "É obrigatório informar o código de um cliente!")
    private Integer cliente;
    @NotNull(message = "É obrigatório informar o valor total do pedido!")
    private BigDecimal total;
    @NotEmptyList(message = "A lista de itens deve ser informada!")
    private List<ItemPedidoDTO> items;
}

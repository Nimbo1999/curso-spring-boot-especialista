package io.github.nimbo1999.rest.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderItemInfoDTO {
    private String descricaoProduto;
    private BigDecimal precoUnitario;
    private Integer quantidade; 
}

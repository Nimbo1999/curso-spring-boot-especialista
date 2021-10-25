package io.github.nimbo1999.rest.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class OrderInfoDTO {
    private Integer codigo;
    private String customerPersonId;
    private String customerName;
    private BigDecimal total;
    private Instant dataPedido;
    private String status;
    private List<OrderItemInfoDTO> items;
}

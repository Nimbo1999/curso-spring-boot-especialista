package io.github.nimbo1999.adapter;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import io.github.nimbo1999.domain.entity.Customer;
import io.github.nimbo1999.domain.entity.ItemPedido;
import io.github.nimbo1999.domain.entity.Pedido;
import io.github.nimbo1999.domain.entity.Produto;
import io.github.nimbo1999.rest.dto.OrderInfoDTO;
import io.github.nimbo1999.rest.dto.OrderItemInfoDTO;

public class PedidoAdapter {
    public static OrderInfoDTO orderEntityToDTO(Pedido pedido) {
        Customer customer = pedido.getCustomer();

        return OrderInfoDTO.builder()
            .codigo(pedido.getId())
            .dataPedido(
                pedido
                    .getDataPedido()
                    .atZone(ZoneId.systemDefault())
                    .format(DateTimeFormatter.ISO_INSTANT)
            )
            .customerPersonId(customer.getCpf())
            .customerName(customer.getNome())
            .total(pedido.getTotal())
            .items(PedidoAdapter.orderItemPedidoEntityToDto(pedido.getItens()))
            .build();
    }

    public static List<OrderItemInfoDTO> orderItemPedidoEntityToDto(List<ItemPedido> orderList) {
        if (CollectionUtils.isEmpty(orderList)) {
            return Collections.emptyList();
        }

        return orderList.stream()
            .map(PedidoAdapter::orderItemPedidoEntityToDto)
            .collect(Collectors.toList());
    }

    public static OrderItemInfoDTO orderItemPedidoEntityToDto(ItemPedido order) {
        Produto produto = order.getProduto();

        return OrderItemInfoDTO.builder()
            .descricaoProduto(produto.getDescricao())
            .precoUnitario(produto.getPreco())
            .quantidade(order.getQuantidade())
            .build();
    }
}

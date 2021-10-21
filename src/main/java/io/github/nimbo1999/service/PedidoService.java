package io.github.nimbo1999.service;

import io.github.nimbo1999.domain.entity.Pedido;
import io.github.nimbo1999.rest.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar( PedidoDTO dto );
}

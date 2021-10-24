package io.github.nimbo1999.service;

import java.util.Optional;

import io.github.nimbo1999.domain.entity.Pedido;
import io.github.nimbo1999.rest.dto.PedidoDTO;

public interface PedidoService {
    Pedido salvar(PedidoDTO dto);
    Optional<Pedido> obterPedidoCompleto(Integer id);
}

package io.github.nimbo1999.rest.controller;

import io.github.nimbo1999.adapter.PedidoAdapter;
import io.github.nimbo1999.domain.entity.Pedido;
import io.github.nimbo1999.rest.dto.OrderInfoDTO;
import io.github.nimbo1999.rest.dto.PedidoDTO;
import io.github.nimbo1999.service.PedidoService;
// Esse import foi substituido por um static import, logo consigo acessar o Enum HttpStatus.CREATED
// apenas com CREATED.
// import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Integer save( @RequestBody PedidoDTO dto ){
        Pedido pedido = service.salvar(dto);
        return pedido.getId();
    }

    @GetMapping("{orderId}")
    @ResponseStatus(OK)
    public OrderInfoDTO getById(@PathVariable Integer orderId) {
        return service
            .obterPedidoCompleto(orderId)
            .map(PedidoAdapter::orderEntityToDTO)
            .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Pedido nao encontrado"));
    }

}

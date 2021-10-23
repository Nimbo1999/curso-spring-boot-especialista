package io.github.nimbo1999.service.impl;

import io.github.nimbo1999.domain.entity.Cliente;
import io.github.nimbo1999.domain.entity.ItemPedido;
import io.github.nimbo1999.domain.entity.Pedido;
import io.github.nimbo1999.domain.entity.Produto;
import io.github.nimbo1999.domain.repository.Clientes;
import io.github.nimbo1999.domain.repository.ItemsPedido;
import io.github.nimbo1999.domain.repository.Pedidos;
import io.github.nimbo1999.domain.repository.Produtos;
import io.github.nimbo1999.exception.RegraNegocioException;
import io.github.nimbo1999.rest.dto.ItemPedidoDTO;
import io.github.nimbo1999.rest.dto.PedidoDTO;
import io.github.nimbo1999.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final Pedidos repository;
    private final Clientes clientesRepository;
    private final Produtos produtosRepository;
    private final ItemsPedido itemsPedidoRepository;

    @Override
    @Transactional
    public Pedido salvar( PedidoDTO dto ) {
        Cliente cliente = clientesRepository
                .findById(dto.getCliente())
                .orElseThrow(() -> new RegraNegocioException("Código de cliente inválido."));

        Pedido pedido = new Pedido();
        pedido.setTotal(dto.getTotal());
        pedido.setDataPedido(LocalDate.now());
        pedido.setCliente(cliente);

        List<ItemPedido> itemsPedido = converterItems(pedido, dto.getItems());
        repository.save(pedido);
        itemsPedidoRepository.saveAll(itemsPedido);
        pedido.setItens(itemsPedido);
        return pedido;
    }

    private List<ItemPedido> converterItems(Pedido pedido, List<ItemPedidoDTO> items){
        if(items.isEmpty()){
            throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
        }

        return items
            .stream()
            .map(dto -> converterItem(pedido, dto))
            .collect(Collectors.toList());
    }

    private ItemPedido converterItem(Pedido pedido, ItemPedidoDTO itemPedidoDto) {
        Integer idProduto = itemPedidoDto.getProduto();
        Produto produto = produtosRepository
            .findById(idProduto)
            .orElseThrow(() -> new RegraNegocioException(
                "Código de produto inválido: "+ idProduto
            ));

        ItemPedido itemPedido = new ItemPedido();
        itemPedido.setQuantidade(itemPedidoDto.getQuantidade());
        itemPedido.setPedido(pedido);
        itemPedido.setProduto(produto);
        return itemPedido;
    }
}

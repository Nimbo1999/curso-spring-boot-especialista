package io.github.nimbo1999.adapter;

import java.util.List;
import java.util.stream.Collectors;

import io.github.nimbo1999.domain.entity.Cliente;
import io.github.nimbo1999.rest.dto.ClientDTO;

public class ClientAdapter {

    public static Cliente dtoToEntity(ClientDTO clientDto) {
        Cliente client = new Cliente();
        client.setId(clientDto.getId());
        client.setCpf(clientDto.getPersonId());
        client.setNome(clientDto.getName());
        return client;
    }

    public static ClientDTO entityToDto(Cliente cliente) {
        ClientDTO clientDto = new ClientDTO();
        clientDto.setId(cliente.getId());
        clientDto.setName(cliente.getNome());
        clientDto.setPersonId(cliente.getCpf());
        return clientDto;
    }

    public static List<ClientDTO> entityListToDtoList(List<Cliente> clientes) {
        return clientes
            .stream()
            .map(ClientAdapter::entityToDto)
            .collect(Collectors.toList());
    }

}

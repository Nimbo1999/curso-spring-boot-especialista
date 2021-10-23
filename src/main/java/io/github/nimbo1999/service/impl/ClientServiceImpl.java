package io.github.nimbo1999.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import io.github.nimbo1999.adapter.ClientAdapter;
import io.github.nimbo1999.domain.entity.Cliente;
import io.github.nimbo1999.domain.repository.Clientes;
import io.github.nimbo1999.exception.RegraNegocioException;
import io.github.nimbo1999.rest.dto.ClientDTO;
import io.github.nimbo1999.service.ClientService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final Clientes clientRepository;

    @Override
    public ClientDTO findById(Integer id) {
        Cliente persistedClient = clientRepository
            .findById(id)
            .orElseThrow(() -> new RegraNegocioException("Cliente não encontrado!"));

        return ClientAdapter.entityToDto(persistedClient);
    }

    @Override
    public ClientDTO save(ClientDTO client) {
        if (client.getName() == null) {
            throw new RegraNegocioException("É obrigatório informar o nome do usuário");
        }
        Cliente newClient = ClientAdapter.dtoToEntity(client);
        Integer clientId = clientRepository.save(newClient).getId();
        client.setId(clientId);
        return client;
    }

    @Override
    public void delete(Integer clientId) {
        Optional<Cliente> cliente = clientRepository.findById(clientId);
        if (cliente.isEmpty()) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cliente não encontrado"
            );
        }
        clientRepository.delete(cliente.get());
    }

    @Override
    public void update(Integer clientId, ClientDTO client) {
        clientRepository
            .findById(clientId)
            .map(result -> {
                Cliente persistedClient = result;
                if (client.getName() != null) {
                    persistedClient.setNome(client.getName());
                }
                if (client.getPersonId() != null) {
                    persistedClient.setCpf(client.getPersonId());
                }
                return clientRepository.save(persistedClient);
            })
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Cliente não encontrado")
            );
    }

    @Override
    public List<ClientDTO> findAll(ClientDTO filter) {
        ExampleMatcher matcher = ExampleMatcher
            .matching()
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Cliente> example = Example.of(ClientAdapter.dtoToEntity(filter), matcher);
        return ClientAdapter.entityListToDtoList(clientRepository.findAll(example));
    }
    
}

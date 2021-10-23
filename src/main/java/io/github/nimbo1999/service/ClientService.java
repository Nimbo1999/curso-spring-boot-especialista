package io.github.nimbo1999.service;

import java.util.List;

import io.github.nimbo1999.rest.dto.ClientDTO;

public interface ClientService {
    ClientDTO findById(Integer id);
    ClientDTO save(ClientDTO client);
    void delete(Integer clientId);
    void update(Integer id, ClientDTO client);
    List<ClientDTO> findAll(ClientDTO filter);
}

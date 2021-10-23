package io.github.nimbo1999.rest.controller;

import io.github.nimbo1999.rest.dto.ClientDTO;
import io.github.nimbo1999.service.impl.ClientServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClientServiceImpl service;

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ClientDTO getClienteById( @PathVariable Integer id ) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDTO save( @RequestBody ClientDTO client ){
        return service.save(client);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Integer id ){
        service.delete(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id, @RequestBody ClientDTO client) {
        service.update(id, client);
    }

    @GetMapping
    public List<ClientDTO> find(ClientDTO filter) {
        return service.findAll(filter);
    }

}

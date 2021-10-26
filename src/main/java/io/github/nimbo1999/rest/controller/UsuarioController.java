package io.github.nimbo1999.rest.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.github.nimbo1999.domain.entity.Usuario;
import io.github.nimbo1999.rest.dto.UsuarioResponseDTO;
import io.github.nimbo1999.service.impl.UsuarioServiceImpl;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {
    
    private final UsuarioServiceImpl usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponseDTO salvar(@RequestBody @Valid Usuario user) {
        return usuarioService.salvar(user);
    }

}

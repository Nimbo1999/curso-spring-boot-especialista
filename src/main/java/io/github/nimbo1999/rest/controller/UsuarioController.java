package io.github.nimbo1999.rest.controller;

import java.util.HashMap;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.nimbo1999.auth.jwt.JwtService;
import io.github.nimbo1999.domain.entity.Usuario;
import io.github.nimbo1999.exception.InvalidPasswordException;
import io.github.nimbo1999.rest.dto.AuthenticateRequestDTO;
import io.github.nimbo1999.rest.dto.AuthenticateResponseDTO;
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

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.OK)
    public AuthenticateResponseDTO authenticate(@RequestBody @Valid AuthenticateRequestDTO credentials) {
        Usuario usuario = Usuario.builder()
            .login(credentials.getLogin())
            .senha(credentials.getSenha())
            .build();

        try {
            HashMap<String, String> authenticationResult = usuarioService.authenticate(usuario);
            Long expirationTime = Long.parseLong(authenticationResult.get(JwtService.resultExp));

            return AuthenticateResponseDTO.builder()
                .accessToken(authenticationResult.get(JwtService.resultJwt))
                .tokenType("Bearer")
                .expiresIn(expirationTime)
                .build();
        } catch (UsernameNotFoundException | InvalidPasswordException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        }
    }

}

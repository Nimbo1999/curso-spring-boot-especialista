package io.github.nimbo1999.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.nimbo1999.domain.entity.Usuario;
import io.github.nimbo1999.domain.repository.UsuarioRepository;
import io.github.nimbo1999.rest.dto.UsuarioResponseDTO;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public UsuarioResponseDTO salvar(Usuario user) {
        String senhaEncoded = passwordEncoder.encode(user.getSenha());
        user.setSenha(senhaEncoded);
        usuarioRepository.save(user);
        return UsuarioResponseDTO.builder()
            .login(user.getLogin())
            .isAdmin(user.isAdmin())
            .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = usuarioRepository
            .findByLogin(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        String[] roles = user.isAdmin() ? new String[]{ "ADMIN", "USER" } : new String[]{ "USER" };

        return User.builder()
            .username(user.getLogin())
            .password(user.getSenha())
            .roles(roles)
            .build();
    }
    
}

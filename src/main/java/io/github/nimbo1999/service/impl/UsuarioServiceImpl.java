package io.github.nimbo1999.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.nimbo1999.auth.jwt.JwtService;
import io.github.nimbo1999.domain.entity.Usuario;
import io.github.nimbo1999.domain.repository.UsuarioRepository;
import io.github.nimbo1999.exception.InvalidPasswordException;
import io.github.nimbo1999.rest.dto.UsuarioResponseDTO;

@Service
public class UsuarioServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JwtService jwtService;

    @Transactional
    public UsuarioResponseDTO salvar(Usuario user) {
        String senhaEncoded = passwordEncoder.encode(user.getSenha());
        user.setSenha(senhaEncoded);
        Integer id = usuarioRepository.save(user).getId();
        return UsuarioResponseDTO.builder()
            .id(id)
            .isAdmin(user.isAdmin())
            .build();
    }

    public HashMap<String, String> authenticate(Usuario usuario) {
        UserDetails userDetails = loadUserByUsername(usuario.getLogin());
        boolean passwordIsCorrect = passwordEncoder.matches(usuario.getSenha(), userDetails.getPassword());
        if (passwordIsCorrect) {
            Usuario user = usuarioRepository
                .findByLogin(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

            return jwtService.gerarToken(user);
        }
        throw new InvalidPasswordException();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = usuarioRepository
            .findByLogin(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return buildUser(user);
    }

    public UserDetails getUserById(Integer userId) throws UsernameNotFoundException {
        Usuario user = usuarioRepository
            .findById(userId)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        return buildUser(user);
    }

    private UserDetails buildUser(Usuario user) {
        String[] roles = user.isAdmin() ? new String[]{ "ADMIN", "USER" } : new String[]{ "USER" };

        return User.builder()
            .username(user.getLogin())
            .password(user.getSenha())
            .roles(roles)
            .build();
    }
    
}

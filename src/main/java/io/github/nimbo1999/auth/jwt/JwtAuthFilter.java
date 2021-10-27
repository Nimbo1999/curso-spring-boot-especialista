package io.github.nimbo1999.auth.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import io.github.nimbo1999.service.impl.UsuarioServiceImpl;

public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UsuarioServiceImpl usuarioService;

    public JwtAuthFilter(JwtService jwtService, UsuarioServiceImpl usuarioService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest req,
        HttpServletResponse res,
        FilterChain filterChain
    ) throws ServletException, IOException {
        String authorizationHeader = req.getHeader("Authorization");
        if (hasAuthorizationHeader(authorizationHeader)) {
            String[] splitedAuthorizationHeader = authorizationHeader.split(" ");
            String jwtToken = splitedAuthorizationHeader[1];
            if (jwtService.isValidToken(jwtToken)) {
                UserDetails userDetails = getJwtUserDetails(jwtToken);
                setAuthorizationToSecurityContext(userDetails);
            }
        }
        filterChain.doFilter(req, res);
    }

    private boolean hasAuthorizationHeader(String authorizationHeader) {
        return authorizationHeader != null && authorizationHeader.startsWith("Bearer");
    }

    private UserDetails getJwtUserDetails(String jwtToken) {
        Integer userId = jwtService.getJWTUserId(jwtToken);
        return usuarioService.getUserById(userId);
    }

    private void setAuthorizationToSecurityContext(UserDetails userDetails) {
        UsernamePasswordAuthenticationToken authentication =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    
}

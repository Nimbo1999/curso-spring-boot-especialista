package io.github.nimbo1999.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

import io.github.nimbo1999.VendasApplication;
import io.github.nimbo1999.domain.entity.Usuario;
import io.github.nimbo1999.utils.InstantUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.JwtMap;

@Service
public class JwtService {

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public String gerarToken(Usuario usuario) {
        Long expirationLong = Long.valueOf(expiracao);
        Long now = InstantUtils
            .instantNow()
            .getEpochSecond();
        Long dataHoraExpiracao = InstantUtils
            .instantNow()
            .plusSeconds(expirationLong * 60)
            .getEpochSecond();

        JwtMap claimMap = new JwtMap();
        claimMap.put(Claims.SUBJECT, usuario.getId().toString());
        claimMap.put(Claims.EXPIRATION, dataHoraExpiracao);
        claimMap.put(Claims.ISSUED_AT, now);
        claimMap.put("login", usuario.getLogin());

        return Jwts.builder()
            .setClaims(claimMap)
            .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
            .compact();
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(VendasApplication.class, args);
        Usuario user = Usuario.builder()
            .login("matheus")
            .admin(true)
            .id(1)
            .build();
        String jwt = context.getBean(JwtService.class).gerarToken(user);
        System.out.println(jwt);
    }
    
}

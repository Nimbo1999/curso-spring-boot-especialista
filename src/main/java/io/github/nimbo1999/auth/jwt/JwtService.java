package io.github.nimbo1999.auth.jwt;

import java.time.Instant;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.github.nimbo1999.domain.entity.Usuario;
import io.github.nimbo1999.utils.InstantUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.JwtMap;

@Service
public class JwtService {
    public static final String resultJwt = "jwt";
    public static final String resultExp = "exp";

    @Value("${security.jwt.expiracao}")
    private String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    private String chaveAssinatura;

    public HashMap<String, String> gerarToken(Usuario usuario) {
        Long expirationLong = Long.valueOf(expiracao) * 60;
        Long now = InstantUtils
            .instantNow()
            .getEpochSecond();
        Long dataHoraExpiracao = InstantUtils
            .instantNow()
            .plusSeconds(expirationLong)
            .getEpochSecond();

        JwtMap claimMap = new JwtMap();
        claimMap.put(Claims.SUBJECT, usuario.getId().toString());
        claimMap.put(Claims.EXPIRATION, dataHoraExpiracao);
        claimMap.put(Claims.ISSUED_AT, now);
        claimMap.put("login", usuario.getLogin());

        String jwt = Jwts.builder()
            .setClaims(claimMap)
            .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
            .compact();

        HashMap<String, String> result = new HashMap<>();
        result.put(resultJwt, jwt);
        result.put(resultExp, expirationLong.toString());
        return result;
    }

    public boolean isValidToken(String token) {
        try {
            Claims claims = getJWTPayload(token);
            Instant expirationDate = claims.getExpiration().toInstant();
            return expirationDate.isAfter(InstantUtils.instantNow());
        } catch (Exception ex) {
            return false;
        }
    }

    public Integer getJWTUserId(String token) throws ExpiredJwtException {
        try {
            return Integer.valueOf(getJWTPayload(token).getSubject());
        } catch (Exception ex) {
            throw new ExpiredJwtException(getJWTHeader(token), getJWTPayload(token), "O campo subject está inválido!");
        }
    }

    private JwsHeader<?> getJWTHeader(String jwtToken) throws ExpiredJwtException {
        return getJWTClaims(jwtToken).getHeader();
    }

    private Claims getJWTPayload(String jwtToken) throws ExpiredJwtException {
        return getJWTClaims(jwtToken).getBody();
    }

    private Jws<Claims> getJWTClaims(String jwtToken) throws ExpiredJwtException {
        return Jwts.parser()
            .setSigningKey(chaveAssinatura)
            .parseClaimsJws(jwtToken);
    }

    // Testando a implementação com:
    // public static void main(String[] args) {
    //     ConfigurableApplicationContext context = SpringApplication.run(VendasApplication.class, args);
    //     Usuario user = Usuario.builder()
    //         .login("matheus")
    //         .admin(true)
    //         .id(1)
    //         .build();
    //     JwtService service = context.getBean(JwtService.class);
    //     String jwt = service.gerarToken(user);
    //     System.out.println("Token JWT:");
    //     System.out.println(jwt);
    //     System.out.println("");
    //     System.out.println("O token está válido? " + service.isValidToken(jwt));
    //     System.out.println("");
    //     System.out.println("User token:");
    //     System.out.println(service.getJWTUserId(jwt));
    // }
    
}

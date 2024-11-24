package com.marketplace.onlinemarketplace.Jwt;

import com.marketplace.onlinemarketplace.entity.User;
import com.marketplace.onlinemarketplace.mongoRepo.JwtRepo;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.swagger.v3.oas.annotations.media.Content;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private final JwtRepo jwtRepo;

    public JwtUtil(JwtRepo jwtRepo) {
        this.jwtRepo = jwtRepo;
    }

    public String generateToken(User user) {

        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256 , secret)
                .compact();

        JwtToken jwtToken = new JwtToken();
        jwtToken.setToken(token);
        jwtToken.setId(String.valueOf(user.getId()));
        jwtToken.setCreatedAt(LocalDateTime.now());
        jwtToken.setExpiresAt(LocalDateTime.now().plusHours(10));
        jwtToken.setRevoked(false);
        jwtToken.setExpired(false);

        jwtRepo.save(jwtToken);
        return token;
    }


    public String extractEmail(String token) {
        String email = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return email;
    }


    public boolean isTokenValid(String token , User user) {
        String email = extractEmail(token);
        boolean isValid = email.equals(user.getEmail()) && !isTokenExpired(token) && !isTokenRevoked(token);
        return isValid;
    }

    private boolean isTokenExpired(String token) {
        boolean email = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());
        return email;
    }

    private boolean isTokenRevoked(String token) {

        JwtToken jwtToken = jwtRepo.findByToken(token).orElse(null);

        boolean revoked = jwtToken != null && jwtToken.isRevoked();
        return revoked;
    }

    public void revokeToken(String token) {
        JwtToken jwtToken = jwtRepo.findByToken(token).orElse(null);
        if(jwtToken != null) {
            jwtToken.setRevoked(true);
            jwtRepo.save(jwtToken);
        }
    }
}

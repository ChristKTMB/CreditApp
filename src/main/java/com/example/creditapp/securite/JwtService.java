package com.example.creditapp.securite;

import com.example.creditapp.model.User;
import com.example.creditapp.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
@Service
public class JwtService {
    private final String ENCRIPTION_KEY = "e357e2f3d7dc4bcd97d970eb502b57b56a52fd1f8ec2428b49b49b65ac8682679a156fa8ab5cb5335acbdb8040a3e60e05adf64d6ef5abf778a419a51bf8bcf1";
    private UserService userService;

    public Map<String, String> generate(String username){
        User user = (User) this.userService.loadUserByUsername(username);
        return this.generateJwt(user);
    }

    public String extractUsername(String token) {
        return this.getClaim(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = this.getClaim(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    private <T> T getClaim(String token, Function<Claims, T> function){
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(this.getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Map<String, String> generateJwt(User user) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 30 * 60 * 1000;

        final Map<String, Object> claims = Map.of(
                "id", user.getId(),
                "email", user.getEmail(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT,  user.getUsername()
        );
        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(user.getUsername())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of("bearer",bearer);
    }

    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(ENCRIPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

    public Long extractUserId(String token) {
        return this.getClaim(token, claims -> claims.get("id", Long.class));
    }

}

package com.blockbank.blockbank.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JWTUtil {
    @Value("${jwt.secret}")
    private String secret;

    private SecretKey key;

    // Token süresi: 24 saat
    private final long expirationMillis = 1000 * 60 * 60 * 24;

    @PostConstruct
    public void init() {
        System.out.println("JWTUtil init çağrıldı");
        key = Keys.hmacShaKeyFor(secret.getBytes());
        System.out.println("JWTUtil key üretildi");
    }

    public String generateToken(String username, String userId, String ethAddress) {
        System.out.println("Token oluşturuluyor kullanıcı: " + username);

        String token = Jwts.builder()
                .subject(username)
                .claim("id", userId)
                .claim("eth_address", ethAddress)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(key)
                .compact();

        System.out.println("Token oluşturuldu: " + token);
        return token;
    }

    public boolean validateToken(String token) {
        System.out.println("Token geçerlilik kontrolü yapılıyor...");
        try {
            boolean expired = isTokenExpired(token);
            System.out.println("Token süresi dolmuş mu?: " + expired);
            return !expired;
        } catch (Exception e) {
            System.err.println("Token doğrulama hatası: " + e.getMessage());
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        Date expiration = parseToken(token).getBody().getExpiration();
        System.out.println("Token expiration date: " + expiration);
        return expiration.before(new Date());
    }

    public String extractUsername(String token) {
        System.out.println("Token'dan kullanıcı adı çıkarılıyor: " + token);
        String username = parseToken(token).getBody().getSubject();
        System.out.println("Token'dan çıkarılan kullanıcı: " + username);
        return username;
    }

    private Jws<Claims> parseToken(String token) {
        System.out.println("Token parse ediliyor: " + token);
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    public String extractEthAddress(String token) {
        return parseToken(token).getBody().get("eth_address", String.class);
    }

    public String extractUserId(String token) {
        return parseToken(token).getBody().get("id", String.class);
    }
}

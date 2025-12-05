package com.example.aivle_4th_MiniProject_team19.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // 간단히 고정 시크릿키 사용 (개발용)
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // 만료시간 (ms)
    private final long ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 30;    // 30분
    private final long REFRESH_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24 * 7; // 7일

    public String generateAccessToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(key)
                .compact();
    }
}

package com.enescidem.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.enescidem.exception.BaseException;
import com.enescidem.exception.ErrorMessage;
import com.enescidem.exception.MessageType;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    // Token imzalamak için güvenli anahtar oluşturuyoruz
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
    
    public String extractUsernameFromHeader(String authHeader) {
    	try {
            String token = authHeader.replace("Bearer ", "").trim();
            Claims claims = Jwts.parserBuilder()
                                .setSigningKey(secret.getBytes())
                                .build()
                                .parseClaimsJws(token)
                                .getBody();
            return claims.getSubject();
		} catch (ExpiredJwtException ex) {
			throw new BaseException(new ErrorMessage(MessageType.TOKEN_EXPIRED, "Authorization Header"));
		}catch (JwtException | IllegalArgumentException ex) {
            throw new BaseException(new ErrorMessage(MessageType.INVALID_TOKEN, "Authorization Header"));
        }
      
    }

    // Token oluşturma (username + role)
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)  // role bilgisini token içine koyuyoruz
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        try {
        	return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
			
		} catch (ExpiredJwtException ex) {
			throw new BaseException(new ErrorMessage(MessageType.TOKEN_EXPIRED, "Token"));
        } catch (JwtException ex) {
            throw new BaseException(new ErrorMessage(MessageType.INVALID_TOKEN, "Token"));
        }
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return (String) extractAllClaims(token).get("role");
    }

    public boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public boolean validateToken(String token, String username) {
        return username.equals(extractUsername(token)) && !isTokenExpired(token);
    }
}

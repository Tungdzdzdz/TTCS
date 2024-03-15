package com.example.project1.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    private static final String SECRET_KEY = "a4TnX1OZyEjWYgrq2vuh9CzD0d5JmVIH7KwlQRkU3bpBefcLMi8o6tPsxFSNAGX";
    public String extractUsername(String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token)
    {
        return extractClaim(token, Claims::getExpiration);
    }

    private boolean isExpiredToken(String token)
    {
        return extractExpiration(token).before(new Date());
    }

    public boolean isValidToken(UserDetails userDetails, String token)
    {
        return userDetails.getUsername().equals(extractUsername(token)) && !isExpiredToken(token);
    }
    public String generateToken(UserDetails userDetails)
    {
        return generateToken(userDetails, new HashMap<String, Object>());
    }
    private String generateToken(
            UserDetails userDetails,
            Map<String, Object> extractClaims
    )
    {
        return Jwts
                .builder()
                .setClaims(extractClaims)
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver)
    {
        final Claims claims = extractAllClaim(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaim(String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignKey()
    {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

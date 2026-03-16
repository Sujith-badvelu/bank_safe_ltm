package com.edutech.progressive.jwt;

import com.edutech.progressive.entity.Customers;
import com.edutech.progressive.repository.CustomerRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    private final CustomerRepository customerRepository;

    @Autowired
    public JwtUtil(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    private final String secret = "secretKey000000000000000000000000000000000000";

    private final long expirationMs = 86400 * 1000;

    public String generateToken(String username) {

        Customers c = customerRepository.findByUsername(username);

        return Jwts.builder()
                .setSubject(username)  // ⭐ FIXED
                .claim("role", c != null ? c.getRole() : "USER")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
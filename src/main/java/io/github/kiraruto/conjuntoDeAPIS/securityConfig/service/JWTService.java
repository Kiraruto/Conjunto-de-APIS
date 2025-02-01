package io.github.kiraruto.conjuntoDeAPIS.securityConfig.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {

    String extractUserName(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);

    boolean isTokenValid2(String token, String email);

    String generateRefreshToken(Map<String, Object> exgtraClaims, UserDetails userDetails);
}

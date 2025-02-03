package io.github.kiraruto.conjuntoDeAPIS.securityConfig.service.impl;

import io.github.kiraruto.conjuntoDeAPIS.securityConfig.service.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTServiceImpl implements JWTService {

    @Value("${jwt.SECRET_KEY}")
    private String secret;

    public String generateToken(UserDetails userDetails) {
            return Jwts.builder().setSubject(userDetails.getUsername())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(getExpirationInMinutes(120))
                    .signWith(getSiginkey(), SignatureAlgorithm.HS256)
                    .compact();
    }

    public String generateRefreshToken(Map<String, Object> exgtraClaims, UserDetails userDetails) {
        return Jwts.builder().setClaims(exgtraClaims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(getExpirationInDays(7))
                .signWith(getSiginkey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }


    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private Key getSiginkey() {
        byte[] key = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(key);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSiginkey()).build().parseClaimsJws(token).getBody();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private Date getExpirationInMinutes(int minutes) {
        return new Date(System.currentTimeMillis() + 1000L * 60 * minutes);
    }

    private Date getExpirationInDays(int days) {
        return new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * days);
    }
}

package io.github.kiraruto.encurtadorurl.domain.users.dto;

public record JwtAuthenticationResponse(String token,
                                        String refreshToken) {
}

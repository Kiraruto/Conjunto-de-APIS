package io.github.kiraruto.conjuntoDeAPIS.model.users.dto;

public record JwtAuthenticationResponse(String token,
                                        String refreshToken) {
}

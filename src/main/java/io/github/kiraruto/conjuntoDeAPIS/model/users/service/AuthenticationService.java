package io.github.kiraruto.conjuntoDeAPIS.model.users.service;

import io.github.kiraruto.conjuntoDeAPIS.model.users.User;
import io.github.kiraruto.conjuntoDeAPIS.model.users.dto.*;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<?> signUp(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SigninRequest signinRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

    User transform(DTOTransform signinRequest);
}

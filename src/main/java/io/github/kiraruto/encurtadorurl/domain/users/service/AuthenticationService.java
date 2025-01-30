package io.github.kiraruto.encurtadorurl.domain.users.service;

import io.github.kiraruto.encurtadorurl.domain.users.User;
import io.github.kiraruto.encurtadorurl.domain.users.dto.JwtAuthenticationResponse;
import io.github.kiraruto.encurtadorurl.domain.users.dto.RefreshTokenRequest;
import io.github.kiraruto.encurtadorurl.domain.users.dto.SignUpRequest;
import io.github.kiraruto.encurtadorurl.domain.users.dto.SigninRequest;

public interface AuthenticationService {

    User signUp(SignUpRequest signUpRequest);

    JwtAuthenticationResponse signin(SigninRequest signinRequest);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}

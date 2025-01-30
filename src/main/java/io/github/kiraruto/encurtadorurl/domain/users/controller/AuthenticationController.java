package io.github.kiraruto.encurtadorurl.domain.users.controller;

import io.github.kiraruto.encurtadorurl.domain.users.dto.RefreshTokenRequest;
import io.github.kiraruto.encurtadorurl.domain.users.dto.SignUpRequest;
import io.github.kiraruto.encurtadorurl.domain.users.dto.SigninRequest;
import io.github.kiraruto.encurtadorurl.domain.users.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest) {
        return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest RefreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(RefreshTokenRequest));
    }
}

package io.github.kiraruto.conjuntoDeAPIS.model.users.controller;

import io.github.kiraruto.conjuntoDeAPIS.model.users.dto.DTOTransform;
import io.github.kiraruto.conjuntoDeAPIS.model.users.dto.RefreshTokenRequest;
import io.github.kiraruto.conjuntoDeAPIS.model.users.dto.SignUpRequest;
import io.github.kiraruto.conjuntoDeAPIS.model.users.dto.SigninRequest;
import io.github.kiraruto.conjuntoDeAPIS.model.users.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest) {
        return authenticationService.signUp(signUpRequest);
    }

    @PostMapping("/login")
    public ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest) {
        return ResponseEntity.ok(authenticationService.signin(signinRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest RefreshTokenRequest) {
        return ResponseEntity.ok(authenticationService.refreshToken(RefreshTokenRequest));
    }

    @PutMapping("/admin")
    public ResponseEntity<?> transformUserInAdmin(@RequestBody DTOTransform dtoUserComplete) {
        return ResponseEntity.ok(authenticationService.transform(dtoUserComplete));
    }
}

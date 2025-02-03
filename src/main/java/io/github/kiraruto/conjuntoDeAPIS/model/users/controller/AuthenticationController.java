package io.github.kiraruto.conjuntoDeAPIS.model.users.controller;

import io.github.kiraruto.conjuntoDeAPIS.model.users.dto.*;
import io.github.kiraruto.conjuntoDeAPIS.model.users.service.AuthenticationService;
import jakarta.transaction.Transactional;
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
        return authenticationService.signin(signinRequest);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody RefreshTokenRequest RefreshTokenRequest) {
        return authenticationService.refreshToken(RefreshTokenRequest);
    }

    @PutMapping("/admin")
    public ResponseEntity<?> transformUserInAdmin(@RequestBody DTOTransform dtoUserComplete) {
        return authenticationService.transformUserInAdmin(dtoUserComplete);
    }

    @PutMapping("/user")
    public ResponseEntity<?> transformAdminInUser(@RequestBody DTOTransform dtoUserComplete) {
        return authenticationService.transformAdminInUser(dtoUserComplete);
    }

    @PutMapping("/user/put/{id}/desactive")
    @Transactional
    public ResponseEntity<?> desactive(@PathVariable Long id) {
        return authenticationService.desactiveByIdTrueToFalse(id);
    }

    @PutMapping("/user/put/{id}/active")
    @Transactional
    public ResponseEntity<?> active(@PathVariable Long id) {
        return authenticationService.activeByIdFalseToTrue(id);
    }

    @PutMapping("/user/atualizar/{id}")
    @Transactional
    public ResponseEntity<?> update(@RequestBody UpdateUser signUpRequest, @PathVariable Long id) {
        return authenticationService.updateUser(signUpRequest, id);
    }

    @GetMapping("/user/getAll")
    public ResponseEntity<?> getAllUsers() {
        return authenticationService.allUsers();
    }

    @GetMapping("/user/get/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) { return authenticationService.getUserEmail(email);}
}

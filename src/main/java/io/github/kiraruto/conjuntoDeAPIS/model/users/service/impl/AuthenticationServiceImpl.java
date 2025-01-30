package io.github.kiraruto.conjuntoDeAPIS.model.users.service.impl;

import io.github.kiraruto.conjuntoDeAPIS.model.clima.exception.ResourceNotFoundException;
import io.github.kiraruto.conjuntoDeAPIS.model.users.User;
import io.github.kiraruto.conjuntoDeAPIS.model.users.dto.*;
import io.github.kiraruto.conjuntoDeAPIS.model.users.repository.UserRepository;
import io.github.kiraruto.conjuntoDeAPIS.model.users.role.UserRole;
import io.github.kiraruto.conjuntoDeAPIS.model.users.service.AuthenticationService;
import io.github.kiraruto.conjuntoDeAPIS.model.users.service.JWTService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public ResponseEntity<?> signUp(SignUpRequest signUpRequest) {

        if (userRepository.existsByEmail(signUpRequest.email())) {
            return ResponseEntity.badRequest().body("Usuario já existe");
        }

        User user = new User();
        user.setEmail(signUpRequest.email());
        user.setUsername(signUpRequest.username());
        user.setUserRole(UserRole.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.password()));

        userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    public JwtAuthenticationResponse signin(SigninRequest signinRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.email(), signinRequest.password()));

        var user = userRepository.findByEmail(signinRequest.email()).orElseThrow(() -> new IllegalArgumentException("Invalid Argumented"));
        var jwt = jwtService.generateToken(user);
        var extraClaims = new HashMap<String, Object>();
        var refreshToken = jwtService.generateRefreshToken(extraClaims, user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(jwt, refreshToken);

        return jwtAuthenticationResponse;
    }

    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String userEmail = jwtService.extractUserName(refreshTokenRequest.token());
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        if(jwtService.isTokenValid(refreshTokenRequest.token(), user)) {
             var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse(jwt, refreshTokenRequest.token());

            return jwtAuthenticationResponse;
        }
        return null;
    }

    public User transform(DTOTransform dtoTransform) {
        var saveUser = userRepository.findByEmail(dtoTransform.email());

        if (saveUser.isEmpty()) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }

        User user = saveUser.get();
        user.atualizarUser(dtoTransform);

        userRepository.save(user);

        return user;
    }



}

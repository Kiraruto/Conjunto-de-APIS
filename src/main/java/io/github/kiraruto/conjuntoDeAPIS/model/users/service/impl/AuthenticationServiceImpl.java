package io.github.kiraruto.conjuntoDeAPIS.model.users.service.impl;

import io.github.kiraruto.conjuntoDeAPIS.model.clima.exception.ResourceNotFoundException;
import io.github.kiraruto.conjuntoDeAPIS.model.users.User;
import io.github.kiraruto.conjuntoDeAPIS.model.users.dto.*;
import io.github.kiraruto.conjuntoDeAPIS.model.users.repository.UserRepository;
import io.github.kiraruto.conjuntoDeAPIS.model.users.role.UserRole;
import io.github.kiraruto.conjuntoDeAPIS.model.users.service.AuthenticationService;
import io.github.kiraruto.conjuntoDeAPIS.securityConfig.service.JWTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

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
        try {
            if (userRepository.existsByEmail(signUpRequest.email())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já existe");
            }

            User user = new User();
            user.setEmail(signUpRequest.email());
            user.setUsername(signUpRequest.username());
            user.setUserRole(UserRole.USER);
            user.setPassword(passwordEncoder.encode(signUpRequest.password()));
            user.setActive(signUpRequest.active());

            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar usuário: " + e.getMessage());
        }
    }

    public ResponseEntity<?> signin(SigninRequest signinRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.email(), signinRequest.password()));

            var user = userRepository.findByEmail(signinRequest.email())
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

            var jwt = jwtService.generateToken(user);
            var extraClaims = new HashMap<String, Object>();
            var refreshToken = jwtService.generateRefreshToken(extraClaims, user);

            return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, refreshToken));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao autenticar usuário: " + e.getMessage());
        }
    }

    public ResponseEntity<?> refreshToken(RefreshTokenRequest refreshTokenRequest) {
        try {
            String userEmail = jwtService.extractUserName(refreshTokenRequest.token());
            User user = userRepository.findByEmail(userEmail)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

            if (jwtService.isTokenValid(refreshTokenRequest.token(), user)) {
                var jwt = jwtService.generateToken(user);
                return ResponseEntity.ok(new JwtAuthenticationResponse(jwt, refreshTokenRequest.token()));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar token: " + e.getMessage());
        }
    }

    public ResponseEntity<?> transformUserInAdmin(DTOTransform dtoTransform) {
        try {
            var saveUser = userRepository.findByEmail(dtoTransform.email());

            if (saveUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
            }

            User user = saveUser.get();
            user.atualizarUserInAdmin(dtoTransform);

            userRepository.save(user);

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao transformar usuário em admin: " + e.getMessage());
        }
    }

    public ResponseEntity<?> transformAdminInUser(DTOTransform dtoTransform) {
        try {
            var saveUser = userRepository.findByEmail(dtoTransform.email());

            if (saveUser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
            }

            User user = saveUser.get();
            user.atualizarAdminInUser(dtoTransform);

            userRepository.save(user);

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao transformar admin em usuário: " + e.getMessage());
        }
    }

    public ResponseEntity<?> desactiveByIdTrueToFalse(Long id) {
        try {
            if (!userRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recurso com ID " + id + " não encontrado.");
            }

            var saveUser = userRepository.findById(id);

            User user = saveUser.get();
            user.atualizarActiveTrueToFalse(user);

            userRepository.save(user);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao desativar usuário: " + e.getMessage());
        }
    }

    public ResponseEntity<?> activeByIdFalseToTrue(Long id) {
        try {
            if (!userRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recurso com ID " + id + " não encontrado.");
            }

            var saveUser = userRepository.findById(id);

            User user = saveUser.get();
            user.atualizarActiveFalseToTrue(user);

            userRepository.save(user);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao ativar usuário: " + e.getMessage());
        }
    }

    public ResponseEntity<?> allUsers() {
        try {
            var saveGetUsers = userRepository.findAllByActiveIsTrue();

            List<SignUpRequest> collect = SignUpRequest.fromClimaList(saveGetUsers);

            return ResponseEntity.ok(collect);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar usuários: " + e.getMessage());
        }
    }
}
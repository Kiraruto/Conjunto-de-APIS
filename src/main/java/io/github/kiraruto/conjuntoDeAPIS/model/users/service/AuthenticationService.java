package io.github.kiraruto.conjuntoDeAPIS.model.users.service;

import io.github.kiraruto.conjuntoDeAPIS.model.users.dto.DTOTransform;
import io.github.kiraruto.conjuntoDeAPIS.model.users.dto.RefreshTokenRequest;
import io.github.kiraruto.conjuntoDeAPIS.model.users.dto.SignUpRequest;
import io.github.kiraruto.conjuntoDeAPIS.model.users.dto.SigninRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<?> signUp(SignUpRequest signUpRequest);

    ResponseEntity<?> signin(SigninRequest signinRequest);

    ResponseEntity<?>  refreshToken(RefreshTokenRequest refreshTokenRequest);

    ResponseEntity<?> transformUserInAdmin(DTOTransform signinRequest);

    ResponseEntity<?> transformAdminInUser(DTOTransform dtoTransform);

    ResponseEntity<?> desactiveByIdTrueToFalse(Long id);

    ResponseEntity<?> activeByIdFalseToTrue(Long id);

    ResponseEntity<?> allUsers();
}

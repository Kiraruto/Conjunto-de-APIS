package io.github.kiraruto.conjuntoDeAPIS.model.users.service;

import io.github.kiraruto.conjuntoDeAPIS.model.users.dto.*;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {

    ResponseEntity<?> signUp(SignUpRequest signUpRequest);

    ResponseEntity<?> signin(SigninRequest signinRequest);

    ResponseEntity<?>  refreshToken(RefreshTokenRequest refreshTokenRequest);

    ResponseEntity<?> transformUserInAdmin(DTOTransform signinRequest);

    ResponseEntity<?> transformAdminInUser(DTOTransform dtoTransform);

    ResponseEntity<?> desactiveByIdTrueToFalse(Long id);

    ResponseEntity<?> activeByIdFalseToTrue(Long id);

    ResponseEntity<?> updateUser(UpdateUser signUpRequest, Long id);

    ResponseEntity<?> allUsers();

    ResponseEntity<?> getUserEmail(String email);
}

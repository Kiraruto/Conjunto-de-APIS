package io.github.kiraruto.conjuntoDeAPIS.model.users.dto;

import jakarta.validation.constraints.Email;

public record SigninRequest(@Email
                            String email,
                            String password) {
}

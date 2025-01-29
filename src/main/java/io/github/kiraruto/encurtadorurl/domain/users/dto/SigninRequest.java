package io.github.kiraruto.encurtadorurl.domain.users.dto;

import jakarta.validation.constraints.Email;

public record SigninRequest(@Email
                            String email,
                            String password) {
}

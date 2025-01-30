package io.github.kiraruto.encurtadorurl.domain.users.dto;


import io.github.kiraruto.encurtadorurl.domain.users.role.UserRole;

public record SignUpRequest(Long id,
                            String username,
                            String email,
                            String password,
                            UserRole userRole) {}

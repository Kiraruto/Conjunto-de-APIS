package io.github.kiraruto.conjuntoDeAPIS.model.users.dto;


import io.github.kiraruto.conjuntoDeAPIS.model.users.role.UserRole;

public record SignUpRequest(Long id,
                            String username,
                            String email,
                            String password,
                            UserRole userRole) {}

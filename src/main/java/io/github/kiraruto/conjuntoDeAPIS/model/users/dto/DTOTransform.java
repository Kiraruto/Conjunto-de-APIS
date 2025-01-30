package io.github.kiraruto.conjuntoDeAPIS.model.users.dto;

import jakarta.validation.constraints.Email;
import org.hibernate.annotations.NotFound;

public record DTOTransform(@NotFound @Email String email) {
}

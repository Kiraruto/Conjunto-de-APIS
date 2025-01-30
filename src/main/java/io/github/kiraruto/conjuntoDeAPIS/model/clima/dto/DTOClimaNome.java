package io.github.kiraruto.conjuntoDeAPIS.model.clima.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.hibernate.annotations.NotFound;

public record DTOClimaNome(@NotFound @JsonAlias("cidade") String city) {
}

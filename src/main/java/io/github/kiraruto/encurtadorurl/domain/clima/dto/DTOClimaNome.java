package io.github.kiraruto.encurtadorurl.domain.clima.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.hibernate.annotations.NotFound;

public record DTOClimaNome(@NotFound @JsonAlias("cidade") String city) {
}

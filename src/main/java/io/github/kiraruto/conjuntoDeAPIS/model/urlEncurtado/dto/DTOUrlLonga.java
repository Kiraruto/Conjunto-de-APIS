package io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.hibernate.annotations.NotFound;

public record DTOUrlLonga(@NotFound @JsonAlias("url") String Long) {
}

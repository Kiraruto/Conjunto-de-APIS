package io.github.kiraruto.encurtadorurl.domain.roteiroDeViagens.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.hibernate.annotations.NotFound;

public record DTORoteiroCityEData(@NotFound @JsonAlias("destino") String destination,
                                  @NotFound @JsonAlias("dias") Integer days) {
}

package io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import org.hibernate.annotations.NotFound;

import java.util.List;

public record DTORoteiroCityEData(@NotFound @JsonAlias("destino") String destination,
                                  @NotFound @JsonAlias("dias") Integer days) {

}

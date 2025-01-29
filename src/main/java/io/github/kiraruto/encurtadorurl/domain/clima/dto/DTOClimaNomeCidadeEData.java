package io.github.kiraruto.encurtadorurl.domain.clima.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.NotFound;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public record DTOClimaNomeCidadeEData(@NotFound @JsonAlias("cidade") String city,
                                      @NotFound
                                      @JsonAlias("data")
                                      @JsonFormat(pattern = "yyyy/MM/dd")
                                      @DateTimeFormat(pattern = "yyyy/MM/dd")
                                      LocalDate date) {
}

package io.github.kiraruto.conjuntoDeAPIS.model.clima.repository;

import io.github.kiraruto.conjuntoDeAPIS.model.clima.ApiClima;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ApiClimaRepository extends JpaRepository<ApiClima, Long> {
    ApiClima findByCityAndDate(String local, LocalDate date);
}

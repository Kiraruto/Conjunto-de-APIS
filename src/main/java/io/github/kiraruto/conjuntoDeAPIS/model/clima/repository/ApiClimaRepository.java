package io.github.kiraruto.conjuntoDeAPIS.model.clima.repository;

import io.github.kiraruto.conjuntoDeAPIS.model.clima.ApiClima;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public interface ApiClimaRepository extends JpaRepository<ApiClima, Long> {
    List<ApiClima> findByCityAndDate(String local, LocalDate date);
}

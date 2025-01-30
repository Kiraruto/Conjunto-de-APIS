package io.github.kiraruto.encurtadorurl.domain.clima.repository;

import io.github.kiraruto.encurtadorurl.domain.clima.ApiClima;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ApiClimaRepository extends JpaRepository<ApiClima, Long> {
    ApiClima findByCityAndDate(String local, LocalDate date);
}

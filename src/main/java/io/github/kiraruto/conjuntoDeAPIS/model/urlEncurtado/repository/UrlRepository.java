package io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.repository;

import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.UrlEncurtado;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlEncurtado, Long> {
}

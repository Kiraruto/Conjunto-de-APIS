package io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.repository;

import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.UrlEncurtado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlEncurtado, Long> {
}

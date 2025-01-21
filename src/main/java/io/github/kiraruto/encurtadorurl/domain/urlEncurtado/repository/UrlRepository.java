package io.github.kiraruto.encurtadorurl.domain.urlEncurtado.repository;

import io.github.kiraruto.encurtadorurl.domain.urlEncurtado.UrlEncurtado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlEncurtado, Long> {
}

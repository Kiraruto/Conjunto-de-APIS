package io.github.kiraruto.encurtadorurl.domain.roteiroDeViagens.repository;

import io.github.kiraruto.encurtadorurl.domain.roteiroDeViagens.RoteiroDeViagens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoteiroDeViagensRepository extends JpaRepository<RoteiroDeViagens, Long> {
    @Query("SELECT r FROM RoteiroDeViagens r WHERE r.id = :id")
    RoteiroDeViagens findId(@Param("id") Long id);
}

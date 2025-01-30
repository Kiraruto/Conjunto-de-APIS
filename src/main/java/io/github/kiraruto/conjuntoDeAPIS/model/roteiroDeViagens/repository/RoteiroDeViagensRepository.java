package io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.repository;

import io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.RoteiroDeViagens;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoteiroDeViagensRepository extends JpaRepository<RoteiroDeViagens, Long> {
    @Query("SELECT r FROM RoteiroDeViagens r WHERE r.id = :id")
    RoteiroDeViagens findId(@Param("id") Long id);
}

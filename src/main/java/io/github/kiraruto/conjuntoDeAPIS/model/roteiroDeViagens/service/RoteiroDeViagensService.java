package io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.service;

import io.github.kiraruto.conjuntoDeAPIS.model.clima.exception.ResourceNotFoundException;
import io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.RoteiroDeViagens;
import io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.dto.DTORoteiroCityEData;
import io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.dto.DTORoteiroCompleto;
import io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.http.RoteiroHttp;
import io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.repository.RoteiroDeViagensRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoteiroDeViagensService {

    private final RoteiroHttp roteiroHttp;
    private final RoteiroDeViagensRepository roteiroDeViagensRepository;

    public RoteiroDeViagensService(RoteiroDeViagensRepository roteiroDeViagensRepository, RoteiroHttp roteiroHttp) {
        this.roteiroDeViagensRepository = roteiroDeViagensRepository;
        this.roteiroHttp = roteiroHttp;
    }

    public ResponseEntity<?> save(DTORoteiroCityEData dtoRoteiroCityEData) {
        try {
            var saveRoteiro = roteiroHttp.criarRoteiro(dtoRoteiroCityEData.destination().toLowerCase().replaceAll(" ", "%20"), dtoRoteiroCityEData.days());
            roteiroDeViagensRepository.save(saveRoteiro);
            return ResponseEntity.status(HttpStatus.CREATED).body(saveRoteiro);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar roteiro de viagens: " + e.getMessage());
        }
    }

    public ResponseEntity<?> findAll() {
        try {
            List<RoteiroDeViagens> roteiros = roteiroDeViagensRepository.findAll();
            return ResponseEntity.ok(roteiros);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar roteiros de viagens: " + e.getMessage());
        }
    }

    public ResponseEntity<?> delete(Long id) {
        try {
            if (!roteiroDeViagensRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Recurso com ID " + id + " não encontrado.");
            }

            roteiroDeViagensRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao excluir roteiro de viagens: " + e.getMessage());
        }
    }

    public ResponseEntity<?> findRoteiroById(Long id) {
        try {
            if (!roteiroDeViagensRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Recurso com ID " + id + " não encontrado.");
            }

            RoteiroDeViagens roteiro = roteiroDeViagensRepository.findId(id);
            return ResponseEntity.ok(new DTORoteiroCompleto(roteiro));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar roteiro de viagens: " + e.getMessage());
        }
    }
}
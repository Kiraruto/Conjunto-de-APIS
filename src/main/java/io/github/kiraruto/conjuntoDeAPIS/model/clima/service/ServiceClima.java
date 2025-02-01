package io.github.kiraruto.conjuntoDeAPIS.model.clima.service;

import io.github.kiraruto.conjuntoDeAPIS.model.clima.ApiClima;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.dto.DTOClimaCompletoSemId;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.dto.DTOClimaNome;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.dto.DTOClimaNomeCidadeEData;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.exception.ResourceNotFoundException;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.http.HttpClima;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.repository.ApiClimaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceClima {

    private final HttpClima httpClima;
    private final ApiClimaRepository apiClimaRepository;

    public ServiceClima(HttpClima httpClima, ApiClimaRepository apiClimaRepository) {
        this.httpClima = httpClima;
        this.apiClimaRepository = apiClimaRepository;
    }

    public ResponseEntity<?> saveReal(DTOClimaNome dtoClimaNome) {
        try {
            var save = httpClima.getWeatherReal(dtoClimaNome.city());
            apiClimaRepository.save(save);
            return ResponseEntity.status(HttpStatus.CREATED).body(save);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar dados do clima em tempo real: " + e.getMessage());
        }
    }

    public ResponseEntity<?> saveHistorico(DTOClimaNomeCidadeEData dtoClimaNomeCidadeEData) {
        try {
            var save = httpClima.getWeatherHistorical(dtoClimaNomeCidadeEData.city().replace(" ", "%20"), dtoClimaNomeCidadeEData.date());
            apiClimaRepository.save(save);
            return ResponseEntity.status(HttpStatus.CREATED).body(save);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar dados históricos do clima: " + e.getMessage());
        }
    }

    public ResponseEntity<?> delete(Long id) {
        try {
            if (!apiClimaRepository.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Recurso com ID " + id + " não encontrado.");
            }

            apiClimaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao excluir recurso: " + e.getMessage());
        }
    }

    public ResponseEntity<?> getClima() {
        try {
            var saveGetClima = apiClimaRepository.findAll();

            List<DTOClimaCompletoSemId> collect = DTOClimaCompletoSemId.fromClimaList(saveGetClima);

            return ResponseEntity.ok(collect);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar dados do clima: " + e.getMessage());
        }
    }
}
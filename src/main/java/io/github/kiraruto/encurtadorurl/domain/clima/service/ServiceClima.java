package io.github.kiraruto.encurtadorurl.domain.clima.service;

import io.github.kiraruto.encurtadorurl.domain.clima.ApiClima;
import io.github.kiraruto.encurtadorurl.domain.clima.dto.DTOClimaCompletoSemId;
import io.github.kiraruto.encurtadorurl.domain.clima.dto.DTOClimaNome;
import io.github.kiraruto.encurtadorurl.domain.clima.dto.DTOClimaNomeCidadeEData;
import io.github.kiraruto.encurtadorurl.domain.clima.exception.ResourceNotFoundException;
import io.github.kiraruto.encurtadorurl.domain.clima.http.HttpClima;
import io.github.kiraruto.encurtadorurl.domain.clima.repository.ApiClimaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceClima {

    private final HttpClima httpClima;
    private ApiClimaRepository apiClimaRepository;

    public ServiceClima(HttpClima httpClima, ApiClimaRepository apiClimaRepository) {
        this.httpClima = httpClima;
        this.apiClimaRepository = apiClimaRepository;
    }

    public ApiClima saveReal(DTOClimaNome dtoClimaNome) {
        var save = httpClima.getWeatherReal(dtoClimaNome.city());
        apiClimaRepository.save(save);
        return save;
    }

    public ApiClima saveHistorico(DTOClimaNomeCidadeEData dtoClimaNomeCidadeEData) {
        var save =  httpClima.getWeatherHistorical(dtoClimaNomeCidadeEData.city(), dtoClimaNomeCidadeEData.date());
        apiClimaRepository.save(save);
        return save;
    }

    public ResponseEntity delete(Long id) {
        if (!apiClimaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso com ID " + id + " não encontrado.");
        }

        apiClimaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity getClima() {
        var saveGetClima = apiClimaRepository.findAll();

        List<DTOClimaCompletoSemId> collect = DTOClimaCompletoSemId.fromClimaList(saveGetClima);

        return ResponseEntity.ok(collect);

    }
}

package io.github.kiraruto.conjuntoDeAPIS.model.clima.service;

import io.github.kiraruto.conjuntoDeAPIS.model.clima.ApiClima;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.dto.DTOClimaCompletoSemId;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.dto.DTOClimaNome;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.dto.DTOClimaNomeCidadeEData;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.exception.ResourceNotFoundException;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.http.HttpClima;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.repository.ApiClimaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ServiceClima {

    private final HttpClima httpClima;
    private final ApiClimaRepository apiClimaRepository;

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
        var save = httpClima.getWeatherHistorical(dtoClimaNomeCidadeEData.city(), dtoClimaNomeCidadeEData.date());
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

    public ApiClima getClimaLocalData(String local, LocalDate date) {
        var save = apiClimaRepository.findByCityAndDate(local, date);

        return save;


    }
}

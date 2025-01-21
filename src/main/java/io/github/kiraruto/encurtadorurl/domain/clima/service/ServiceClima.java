package io.github.kiraruto.encurtadorurl.domain.clima.service;

import io.github.kiraruto.encurtadorurl.domain.clima.ApiClima;
import io.github.kiraruto.encurtadorurl.domain.clima.dto.DTOClimaNome;
import io.github.kiraruto.encurtadorurl.domain.clima.dto.DTOClimaNomeCidadeEData;
import io.github.kiraruto.encurtadorurl.domain.clima.http.HttpClima;
import org.springframework.stereotype.Service;

@Service
public class ServiceClima {

    private final HttpClima httpClima;

    public ServiceClima(HttpClima httpClima) {
        this.httpClima = httpClima;
    }

    public ApiClima saveReal(DTOClimaNome dtoClimaNome) {
        return httpClima.getWeatherReal(dtoClimaNome.city());
    }

    public ApiClima saveHistorico(DTOClimaNomeCidadeEData dtoClimaNomeCidadeEData) {
        return httpClima.getWeatherHistorical(dtoClimaNomeCidadeEData.city(), dtoClimaNomeCidadeEData.date());
    }
}

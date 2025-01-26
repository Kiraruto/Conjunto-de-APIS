package io.github.kiraruto.encurtadorurl.domain.clima.dto;

import io.github.kiraruto.encurtadorurl.domain.clima.ApiClima;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record DTOClimaCompletoSemId(String city,
                                    Double temperature,
                                    Double humidity,
                                    Double wind,
                                    String description,
                                    LocalDate date) {

    public static List<DTOClimaCompletoSemId> fromClimaList(List<ApiClima> saveGet) {
        return saveGet.stream()
                .map(a -> new DTOClimaCompletoSemId(a.getCity(),a.getTemperature(),a.getHumidity(),a.getWind(),a.getDescription(),a.getDate()))
                .collect(Collectors.toList());
    }
}

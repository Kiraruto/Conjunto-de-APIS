package io.github.kiraruto.conjuntoDeAPIS.model.clima.dto;

import io.github.kiraruto.conjuntoDeAPIS.model.clima.ApiClima;

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
                .map(a -> new DTOClimaCompletoSemId(a.getCity().replace("%20", " "), a.getTemperature(), a.getHumidity(), a.getWind(), a.getDescription(), a.getDate()))
                .collect(Collectors.toList());
    }
}

package io.github.kiraruto.conjuntoDeAPIS.model.clima.dto;

import io.github.kiraruto.conjuntoDeAPIS.model.clima.ApiClima;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record DTOClimaCompletoSemIdUserId(String city,
                                    Double temperature,
                                    Double humidity,
                                    Double wind,
                                    String description,
                                    LocalDate date,
                                    Long user) {

    public static List<DTOClimaCompletoSemIdUserId> fromClimaList(List<ApiClima> saveGet) {
        return saveGet.stream()
                .map(a -> new DTOClimaCompletoSemIdUserId(a.getCity().replace("%20", " "), a.getTemperature(), a.getHumidity(), a.getWind(), a.getDescription(), a.getDate(), a.getUser().getId()))
                .collect(Collectors.toList());
    }
}

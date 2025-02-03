package io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.dto;

import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.UrlEncurtado;

import java.time.LocalDate;
import java.util.List;

public record DTOUrlCompletoIdUser(String longUrl,
                                   String shortenedUrl,
                                   LocalDate createdAt,
                                   Long user) {

    public static List<DTOUrlCompletoIdUser> fromList(List<UrlEncurtado> saveUrlClima) {
        return saveUrlClima.stream()
                .map(url -> new DTOUrlCompletoIdUser(
                        url.getLongUrl(),
                        url.getShortenedUrl(),
                        url.getCreatedAt(),
                        url.getUser().getId()
                ))
                .toList();
    }
}

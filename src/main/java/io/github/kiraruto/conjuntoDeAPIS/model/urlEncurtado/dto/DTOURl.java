package io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.dto;

import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.UrlEncurtado;

import java.util.List;
import java.util.stream.Collectors;

public record DTOURl(String longUrl,
                     String shortenedUrl) {
    public static List<DTOURl> fromUrlList(List<UrlEncurtado> saveUrlClima) {
        return saveUrlClima.stream()
                .map(s -> new DTOURl(s.getLongUrl(), s.getShortenedUrl()))
                .collect(Collectors.toList());
    }
}

package io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.dto;

import io.github.kiraruto.conjuntoDeAPIS.model.clima.ApiClima;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.dto.DTOClimaCompletoSemIdUserId;
import io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.DiaRoteiro;
import io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.RoteiroDeViagens;
import io.github.kiraruto.conjuntoDeAPIS.model.users.User;

import java.util.List;
import java.util.stream.Collectors;

public record DTORoteiroCompleto(Long id,
                                 String destination,
                                 Integer days,
                                 List<DiaRoteiro> roteiro,
                                 Long user) {
    public DTORoteiroCompleto(RoteiroDeViagens save) {
        this(save.getId(), save.getDestination(), save.getDays(), save.getRoteiro(), save.getId());
    }

    public static List<DTORoteiroCompleto> fromRoteiroList(List<RoteiroDeViagens> saveGet) {
        return saveGet.stream()
                .map(a -> new DTORoteiroCompleto(a.getId(), a.getDestination(), a.getDays(), a.getRoteiro(), a.getUser().getId()))
                .collect(Collectors.toList());
    }
}


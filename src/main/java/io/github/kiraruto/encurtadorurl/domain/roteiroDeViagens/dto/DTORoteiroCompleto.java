package io.github.kiraruto.encurtadorurl.domain.roteiroDeViagens.dto;

import io.github.kiraruto.encurtadorurl.domain.roteiroDeViagens.DiaRoteiro;
import io.github.kiraruto.encurtadorurl.domain.roteiroDeViagens.RoteiroDeViagens;

import java.util.List;

public record DTORoteiroCompleto(String destination,
                                 Integer days,
                                 List<DiaRoteiro> roteiro) {
    public DTORoteiroCompleto(RoteiroDeViagens save) {
        this(save.getDestination(), save.getDays(), save.getRoteiro());
    }
}


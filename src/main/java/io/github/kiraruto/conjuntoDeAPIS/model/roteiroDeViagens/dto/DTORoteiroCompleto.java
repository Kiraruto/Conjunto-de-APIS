package io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.dto;

import io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.DiaRoteiro;
import io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.RoteiroDeViagens;

import java.util.List;

public record DTORoteiroCompleto(String destination,
                                 Integer days,
                                 List<DiaRoteiro> roteiro) {
    public DTORoteiroCompleto(RoteiroDeViagens save) {
        this(save.getDestination(), save.getDays(), save.getRoteiro());
    }
}


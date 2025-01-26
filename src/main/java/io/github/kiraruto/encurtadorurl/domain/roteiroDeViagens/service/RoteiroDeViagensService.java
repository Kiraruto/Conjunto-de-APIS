package io.github.kiraruto.encurtadorurl.domain.roteiroDeViagens.service;

import io.github.kiraruto.encurtadorurl.domain.roteiroDeViagens.RoteiroDeViagens;
import io.github.kiraruto.encurtadorurl.domain.roteiroDeViagens.dto.DTORoteiroCityEData;
import io.github.kiraruto.encurtadorurl.domain.roteiroDeViagens.http.RoteiroHttp;
import org.springframework.stereotype.Service;

@Service
public class RoteiroDeViagensService {

    private RoteiroHttp roteiroHttp;

    public RoteiroDeViagensService(RoteiroHttp roteiroHttp) {
        this.roteiroHttp = roteiroHttp;
    }

    public RoteiroDeViagens save(DTORoteiroCityEData dtoRoteiroCityEData) {
        return roteiroHttp.criarRoteiro(dtoRoteiroCityEData.destination(), dtoRoteiroCityEData.days());
    }
}

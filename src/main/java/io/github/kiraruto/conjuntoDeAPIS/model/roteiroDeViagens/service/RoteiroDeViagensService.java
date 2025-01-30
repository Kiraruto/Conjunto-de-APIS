package io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.service;

import io.github.kiraruto.conjuntoDeAPIS.model.clima.exception.ResourceNotFoundException;
import io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.RoteiroDeViagens;
import io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.dto.DTORoteiroCityEData;
import io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.http.RoteiroHttp;
import io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.repository.RoteiroDeViagensRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoteiroDeViagensService {

    private final RoteiroHttp roteiroHttp;
    private final RoteiroDeViagensRepository roteiroDeViagensRepository;

    public RoteiroDeViagensService(RoteiroDeViagensRepository roteiroDeViagensRepository, RoteiroHttp roteiroHttp) {
        this.roteiroDeViagensRepository = roteiroDeViagensRepository;
        this.roteiroHttp = roteiroHttp;
    }

    public RoteiroDeViagens save(DTORoteiroCityEData dtoRoteiroCityEData) {
        var saveRoteiro = roteiroHttp.criarRoteiro(dtoRoteiroCityEData.destination().toLowerCase().replaceAll(" ", "%20"), dtoRoteiroCityEData.days());
        roteiroDeViagensRepository.save(saveRoteiro);
        return saveRoteiro;
    }

    public List<RoteiroDeViagens> findAll() {
        return roteiroDeViagensRepository.findAll();
    }

    public void delete(Long id) {
        if(!roteiroDeViagensRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso com ID " + id + " não encontrado.");
        }

        roteiroDeViagensRepository.deleteById(id);
    }

    public RoteiroDeViagens findRoteiroById(Long id) {
        if (!roteiroDeViagensRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso com ID " + id + " não encontrado.");
        }

        return roteiroDeViagensRepository.findId(id);
    }
}

package io.github.kiraruto.encurtadorurl.domain.clima.controller;

import io.github.kiraruto.encurtadorurl.domain.clima.dto.DTOClimaNome;
import io.github.kiraruto.encurtadorurl.domain.clima.dto.DTOClimaNomeCidadeEData;
import io.github.kiraruto.encurtadorurl.domain.clima.service.ServiceClima;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clima")
public class ClimaController {

    private final ServiceClima serviceClima;

    public ClimaController(ServiceClima serviceClima) {
        this.serviceClima = serviceClima;
    }

    @PostMapping("/city/real")
    public ResponseEntity postClimaReal(@RequestBody DTOClimaNome dtoClimaNome) {
        return ResponseEntity.ok(serviceClima.saveReal(dtoClimaNome));
    }

    @PostMapping("/city/historico")
    public ResponseEntity postClimaHistorico(@RequestBody DTOClimaNomeCidadeEData dtoClimaNomeCidade) {
        return ResponseEntity.ok(serviceClima.saveHistorico(dtoClimaNomeCidade));
    }
}

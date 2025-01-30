package io.github.kiraruto.conjuntoDeAPIS.model.clima.controller;

import io.github.kiraruto.conjuntoDeAPIS.model.clima.dto.DTOClimaNome;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.dto.DTOClimaNomeCidadeEData;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.service.ServiceClima;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/clima")
public class ClimaController {

    private final ServiceClima serviceClima;

    public ClimaController(ServiceClima serviceClima) {
        this.serviceClima = serviceClima;
    }

    @PostMapping("/city/real")
    @Transactional
    public ResponseEntity postClimaReal(@RequestBody DTOClimaNome dtoClimaNome) {
        return ResponseEntity.ok(serviceClima.saveReal(dtoClimaNome));
    }

    @PostMapping("/city/historico")
    @Transactional
    public ResponseEntity postClimaHistorico(@RequestBody DTOClimaNomeCidadeEData dtoClimaNomeCidade) {
        return ResponseEntity.ok(serviceClima.saveHistorico(dtoClimaNomeCidade));
    }

    @GetMapping
    public ResponseEntity getClimas() {
        return serviceClima.getClima();
    }

    @GetMapping("/{local}/{data}")
    public ResponseEntity getLocalDataClima(@PathVariable String local, @PathVariable LocalDate data) {
        return ResponseEntity.ok(serviceClima.getClimaLocalData(local, data));
    }
}

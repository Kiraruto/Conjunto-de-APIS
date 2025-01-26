package io.github.kiraruto.encurtadorurl.domain.clima.controller;

import io.github.kiraruto.encurtadorurl.domain.clima.dto.DTOClimaNome;
import io.github.kiraruto.encurtadorurl.domain.clima.dto.DTOClimaNomeCidadeEData;
import io.github.kiraruto.encurtadorurl.domain.clima.service.ServiceClima;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public ResponseEntity deleteClimas(@PathVariable Long id) {
        return serviceClima.delete(id);
    }
}

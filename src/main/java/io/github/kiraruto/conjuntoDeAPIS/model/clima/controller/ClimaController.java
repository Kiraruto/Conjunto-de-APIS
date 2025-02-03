package io.github.kiraruto.conjuntoDeAPIS.model.clima.controller;

import io.github.kiraruto.conjuntoDeAPIS.model.clima.dto.DTOClimaNome;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.dto.DTOClimaNomeCidadeEData;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.service.ServiceClima;
import jakarta.transaction.Transactional;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<?> postClimaReal(@RequestBody DTOClimaNome dtoClimaNome) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
        }

        return serviceClima.saveReal(dtoClimaNome, userDetails.getUsername());
    }


    @PostMapping("/city/historico")
    @Transactional
    public ResponseEntity postClimaHistorico(@RequestBody DTOClimaNomeCidadeEData dtoClimaNomeCidade) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não autenticado");
        }

        return serviceClima.saveHistorico(dtoClimaNomeCidade, userDetails.getUsername());
    }

    @GetMapping("/{local}/{localDate}")
    public ResponseEntity<?> GetClimaCidadeEData(
            @PathVariable String local,
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate localDate) {
        return serviceClima.getClimaLocalEData(local, localDate);
    }

    @GetMapping
    public ResponseEntity getClimas() {
        return serviceClima.getClima();
    }
}

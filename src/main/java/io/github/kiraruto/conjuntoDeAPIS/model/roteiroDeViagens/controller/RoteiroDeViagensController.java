package io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.controller;

import io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.dto.DTORoteiroCityEData;
import io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.dto.DTORoteiroCompleto;
import io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.service.RoteiroDeViagensService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roteiro")
public class RoteiroDeViagensController {

    private final RoteiroDeViagensService roteiroDeViagensService;

    public RoteiroDeViagensController(RoteiroDeViagensService roteiroDeViagensService) {
        this.roteiroDeViagensService = roteiroDeViagensService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity postRoteiroDeViagens(@RequestBody DTORoteiroCityEData dtoRoteiroCityEData) {
        var save = roteiroDeViagensService.save(dtoRoteiroCityEData);
        return ResponseEntity.ok(save);
    }

    @GetMapping
    public ResponseEntity getRoteiros() {
        return ResponseEntity.ok(roteiroDeViagensService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity getIdRoteiros(@PathVariable Long id) {
        var save = roteiroDeViagensService.findRoteiroById(id);
        return ResponseEntity.ok(new DTORoteiroCompleto(save));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRoteiros(@PathVariable Long id) {
        roteiroDeViagensService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

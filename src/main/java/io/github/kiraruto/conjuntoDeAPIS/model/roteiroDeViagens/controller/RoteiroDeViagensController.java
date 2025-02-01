package io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.controller;

import io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens.dto.DTORoteiroCityEData;
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
        return roteiroDeViagensService.save(dtoRoteiroCityEData);
    }

    @GetMapping
    public ResponseEntity getRoteiros() {
        return roteiroDeViagensService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getIdRoteiros(@PathVariable Long id) {
        return  roteiroDeViagensService.findRoteiroById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteRoteiros(@PathVariable Long id) {
        return roteiroDeViagensService.delete(id);
    }
}

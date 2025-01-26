package io.github.kiraruto.encurtadorurl.domain.roteiroDeViagens.controller;

import io.github.kiraruto.encurtadorurl.domain.roteiroDeViagens.dto.DTORoteiroCityEData;
import io.github.kiraruto.encurtadorurl.domain.roteiroDeViagens.service.RoteiroDeViagensService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

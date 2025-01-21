package io.github.kiraruto.encurtadorurl.domain.urlEncurtado.controller;

import io.github.kiraruto.encurtadorurl.domain.urlEncurtado.dto.DTOUrlLonga;
import io.github.kiraruto.encurtadorurl.domain.urlEncurtado.service.UrlService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/url")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> postUrl(@RequestBody DTOUrlLonga dtoUrlLong) {
        return urlService.save(dtoUrlLong);
    }
}

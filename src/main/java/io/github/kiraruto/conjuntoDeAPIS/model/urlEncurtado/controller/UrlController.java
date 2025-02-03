package io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.controller;

import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.dto.DTOUrlCompletoIdUser;
import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.dto.DTOUrlLonga;
import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.service.UrlService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/url")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<?> postUrl(@RequestBody DTOUrlLonga dtoUrlLong) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails userDetails)) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }

        return urlService.save(dtoUrlLong, userDetails.getUsername());
    }


    @GetMapping
    public ResponseEntity<?> getAllUrls() {
        return urlService.get();
    }
}

package io.github.kiraruto.encurtadorurl.domain.urlEncurtado.controller;

import io.github.kiraruto.encurtadorurl.domain.urlEncurtado.dto.DTOUrlLonga;
import io.github.kiraruto.encurtadorurl.domain.urlEncurtado.service.UrlService;
import io.github.kiraruto.encurtadorurl.domain.users.service.JWTService;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof UserDetails)) {
            return ResponseEntity.status(401).body("Usuário não autenticado");
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        System.out.println("Usuário autenticado: " + email);

        return urlService.save(dtoUrlLong);
    }

}

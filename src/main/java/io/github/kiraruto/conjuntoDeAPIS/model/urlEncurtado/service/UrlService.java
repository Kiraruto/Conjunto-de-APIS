package io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.UrlEncurtado;
import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.dto.DTOURl;
import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.dto.DTOUrlLonga;
import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.http.UrlHttp;
import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.repository.UrlRepository;
import io.github.kiraruto.conjuntoDeAPIS.model.users.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrlService {

    private final UrlHttp urlHttp;
    private final UrlRepository urlRepository;
    private final AuthenticationService authenticationService;

    public UrlService(UrlHttp urlHttp, UrlRepository urlRepository, AuthenticationService authenticationService) {
        this.urlHttp = urlHttp;
        this.urlRepository = urlRepository;
        this.authenticationService = authenticationService;
    }

    public ResponseEntity save(DTOUrlLonga dtoUrlLong) {
        String saveUrlShorten = urlHttp.urlShortenerClient(dtoUrlLong.Long());
        urlRepository.save(new UrlEncurtado(dtoUrlLong.Long(), minima(saveUrlShorten)));

        return ResponseEntity.ok(saveUrlShorten);
    }

    public ResponseEntity<?> get() {
        var saveUrlClima = urlRepository.findAll();

        List<DTOURl> collect = DTOURl.fromUrlList(saveUrlClima);

        return ResponseEntity.ok(collect);
    }

    private String minima(String response) {
        String shortenedUrl = "Erro ao processar a URL";

        if (response != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonResponse = null;

            try {
                jsonResponse = objectMapper.readTree(response);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            shortenedUrl = jsonResponse.get("urlEncurtada").asText();
        }
        return shortenedUrl;
    }
}

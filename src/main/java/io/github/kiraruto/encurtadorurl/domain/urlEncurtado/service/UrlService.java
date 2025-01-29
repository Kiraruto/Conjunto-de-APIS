package io.github.kiraruto.encurtadorurl.domain.urlEncurtado.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kiraruto.encurtadorurl.domain.urlEncurtado.UrlEncurtado;
import io.github.kiraruto.encurtadorurl.domain.urlEncurtado.dto.DTOUrlLonga;
import io.github.kiraruto.encurtadorurl.domain.urlEncurtado.http.UrlHttp;
import io.github.kiraruto.encurtadorurl.domain.urlEncurtado.repository.UrlRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UrlService {

    private final UrlHttp urlHttp;
    private final UrlRepository urlRepository;

    public UrlService(UrlHttp urlHttp, UrlRepository urlRepository) {
        this.urlHttp = urlHttp;
        this.urlRepository = urlRepository;
    }

    public ResponseEntity save(DTOUrlLonga dtoUrlLong) {
        String saveUrlShorten = urlHttp.urlShortenerClient(dtoUrlLong.Long());
        urlRepository.save(new UrlEncurtado(dtoUrlLong.Long(), minima(saveUrlShorten)));

        return ResponseEntity.ok(saveUrlShorten);
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

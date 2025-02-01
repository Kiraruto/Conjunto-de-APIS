package io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.UrlEncurtado;
import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.dto.DTOURl;
import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.dto.DTOUrlLonga;
import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.http.UrlHttp;
import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.repository.UrlRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrlService {

    private final UrlHttp urlHttp;
    private final UrlRepository urlRepository;

    public UrlService(UrlHttp urlHttp, UrlRepository urlRepository) {
        this.urlHttp = urlHttp;
        this.urlRepository = urlRepository;
    }

    public ResponseEntity<?> save(DTOUrlLonga dtoUrlLong) {
        try {
            String saveUrlShorten = urlHttp.urlShortenerClient(dtoUrlLong.Long());
            if (saveUrlShorten == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erro ao encurtar a URL: resposta nula do serviço externo.");
            }

            String shortenedUrl = minima(saveUrlShorten);
            if (shortenedUrl.equals("Erro ao processar a URL")) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erro ao processar a resposta do serviço de encurtamento de URL.");
            }

            urlRepository.save(new UrlEncurtado(dtoUrlLong.Long(), shortenedUrl));
            return ResponseEntity.ok(shortenedUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar a URL encurtada: " + e.getMessage());
        }
    }

    public ResponseEntity<?> get() {
        try {
            var saveUrlClima = urlRepository.findAll();
            List<DTOURl> collect = DTOURl.fromUrlList(saveUrlClima);
            return ResponseEntity.ok(collect);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar URLs encurtadas: " + e.getMessage());
        }
    }

    private String minima(String response) {
        String shortenedUrl = "Erro ao processar a URL";

        if (response != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode jsonResponse = objectMapper.readTree(response);
                if (jsonResponse.has("urlEncurtada")) {
                    shortenedUrl = jsonResponse.get("urlEncurtada").asText();
                }
            } catch (JsonProcessingException e) {
                shortenedUrl = "Erro ao processar a URL";
            }
        }
        return shortenedUrl;
    }
}
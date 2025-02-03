package io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.UrlEncurtado;
import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.dto.DTOUrlCompletoIdUser;
import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.dto.DTOUrlLonga;
import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.http.UrlHttp;
import io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado.repository.UrlRepository;
import io.github.kiraruto.conjuntoDeAPIS.model.users.User;
import io.github.kiraruto.conjuntoDeAPIS.model.users.repository.UserRepository;
import io.github.kiraruto.conjuntoDeAPIS.model.users.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UrlService {

    private final UrlHttp urlHttp;
    private final UrlRepository urlRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public UrlService(UrlHttp urlHttp, UrlRepository urlRepository, UserService userService, UserRepository userRepository) {
        this.urlHttp = urlHttp;
        this.urlRepository = urlRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> save(DTOUrlLonga dtoUrlLong, String emailUsuario) {
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

            Optional<User> userOptional = userRepository.findByEmail(emailUsuario);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado.");
            }

            User user = userOptional.get();
            UrlEncurtado urlEncurtado = new UrlEncurtado(dtoUrlLong.Long(), shortenedUrl, user);

            urlRepository.save(urlEncurtado);
            user.addUrlEncurtada(urlEncurtado);

            return ResponseEntity.ok(shortenedUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar a URL encurtada: " + e.getMessage());
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

    public ResponseEntity<?> get() {
        try {
            List<UrlEncurtado> saveUrlClima = urlRepository.findAll();
            List<DTOUrlCompletoIdUser> listaDto = DTOUrlCompletoIdUser.fromList(saveUrlClima);
            return ResponseEntity.ok(listaDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar URLs encurtadas: " + e.getMessage());
        }
    }
}
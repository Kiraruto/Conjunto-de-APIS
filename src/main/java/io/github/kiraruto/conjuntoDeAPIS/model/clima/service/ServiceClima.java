package io.github.kiraruto.conjuntoDeAPIS.model.clima.service;

import io.github.kiraruto.conjuntoDeAPIS.model.clima.ApiClima;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.dto.DTOClimaCompletoSemIdUserId;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.dto.DTOClimaNome;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.dto.DTOClimaNomeCidadeEData;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.http.HttpClima;
import io.github.kiraruto.conjuntoDeAPIS.model.clima.repository.ApiClimaRepository;
import io.github.kiraruto.conjuntoDeAPIS.model.users.User;
import io.github.kiraruto.conjuntoDeAPIS.model.users.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceClima {

    private final HttpClima httpClima;
    private final ApiClimaRepository apiClimaRepository;
    private final UserRepository userRepository;

    public ServiceClima(HttpClima httpClima, ApiClimaRepository apiClimaRepository, UserRepository userRepository) {
        this.httpClima = httpClima;
        this.apiClimaRepository = apiClimaRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> saveReal(DTOClimaNome dtoClimaNome, String authentication) {
        try {
            var save = httpClima.getWeatherReal(dtoClimaNome.city());

            var saveGetClima = returnClimaLocalEData(save.getCity(), save.getDate());

            if (!saveGetClima.isEmpty()) {
                var firtsSaveGetClima = saveGetClima.get(0);

                if (firtsSaveGetClima.getCity().equalsIgnoreCase(save.getCity()) && firtsSaveGetClima.getDate().equals(save.getDate())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body("Já existe um Clima com esta DATA e este CLIMA. \nVá até esta URL: /clima e use o método GET");
                }
            }

            apiClimaRepository.save(save);

            Optional<User> userOptional = userRepository.findByEmail(authentication);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado.");
            }

            User user = userOptional.get();

            user.addClima(save);

            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(save);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar dados do clima em tempo real: " + e.getMessage());
        }
    }


    public ResponseEntity<?> saveHistorico(DTOClimaNomeCidadeEData dtoClimaNomeCidadeEData, String authentication) {
        try {

            var saveGetClima = returnClimaLocalEData(dtoClimaNomeCidadeEData.city(), dtoClimaNomeCidadeEData.date());

            if (!saveGetClima.isEmpty()) {
                var firtsSaveGetClima = saveGetClima.get(0);

                if (firtsSaveGetClima.getCity().equalsIgnoreCase(dtoClimaNomeCidadeEData.city()) && firtsSaveGetClima.getDate().equals(dtoClimaNomeCidadeEData.date())) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body("Já existe um Clima com esta DATA e este CLIMA. \nVá até esta URL: /clima e use o método GET");
                }
            }

            var save = httpClima.getWeatherHistorical(dtoClimaNomeCidadeEData.city().replace(" ", "%20"), dtoClimaNomeCidadeEData.date());
            apiClimaRepository.save(save);

            Optional<User> userOptional = userRepository.findByEmail(authentication);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado.");
            }

            User user = userOptional.get();
            user.addClima(save);
            userRepository.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(save);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar dados históricos do clima: " + e.getMessage());
        }
    }

    public ResponseEntity<?> getClima() {
        try {
            List<ApiClima> saveGet = apiClimaRepository.findAll();
            List<DTOClimaCompletoSemIdUserId> listaDto = DTOClimaCompletoSemIdUserId.fromClimaList(saveGet);

            return ResponseEntity.ok(listaDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar dados do clima: " + e.getMessage());
        }
    }

    public ResponseEntity<?> getClimaLocalEData(String local, LocalDate localDate) {

        var replaceOne = local.replaceAll("\\+", " ");
        var replaceFinal = replaceOne.replaceAll("%20", " ");

        try {
            var saveGetClima = returnClimaLocalEData(replaceFinal, localDate);

            if (saveGetClima == null || !saveGetClima.get(0).getCity().equalsIgnoreCase(replaceFinal) || !saveGetClima.get(0).getDate().equals(localDate)) {
                return ResponseEntity.notFound().build();
            }

            List<DTOClimaCompletoSemIdUserId> listaDto = DTOClimaCompletoSemIdUserId.fromClimaList(saveGetClima);

            return ResponseEntity.ok(listaDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao buscar dados do clima com base no local: " + replaceFinal + " data: " + localDate + " " + e.getMessage());
        }
    }

    private List<ApiClima> returnClimaLocalEData(String local, LocalDate localDate) {
        return apiClimaRepository.findByCityAndDate(local, localDate);
    }
}
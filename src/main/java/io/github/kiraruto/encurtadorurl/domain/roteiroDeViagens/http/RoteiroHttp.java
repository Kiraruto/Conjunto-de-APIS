package io.github.kiraruto.encurtadorurl.domain.roteiroDeViagens.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kiraruto.encurtadorurl.domain.roteiroDeViagens.RoteiroDeViagens;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoteiroHttp {

    private static final Logger logger = LoggerFactory.getLogger(RoteiroHttp.class);

    @Value("${Roteiro.api.key}")
    private String apikey;

    @Value("${Roteiro.api.endpointDiscover}")
    private String ENDPOINT_DISCOVER;

    @Value("${Roteiro.api.endpointGeocoding}")
    private String ENDPOINT_GEOCODING;

    private double[] obterCoordenadasDaCidade(String cidade) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?q=%s&apikey=%s", ENDPOINT_GEOCODING, cidade, apikey);

        try {
            String response = restTemplate.getForObject(url, String.class);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response);

            JsonNode itemsNode = rootNode.path("items");
            if (!itemsNode.isArray() || itemsNode.isEmpty()) {
                String errorMessage = "Nenhum resultado encontrado para a cidade: " + cidade;
                logger.error(errorMessage);
                throw new RuntimeException(errorMessage);
            }

            JsonNode positionNode = itemsNode.get(0).path("position");
            if (positionNode.isMissingNode()) {
                String errorMessage = "Campo 'position' não encontrado no JSON de resposta.";
                logger.error(errorMessage);
                throw new RuntimeException(errorMessage);
            }

            double lat = positionNode.path("lat").asDouble();
            double lng = positionNode.path("lng").asDouble();

            logger.info("Coordenadas: {}, {}", lat, lng);
            return new double[]{lat, lng};
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            logger.error("Erro ao consumir a API de geocoding: {} - {}", e.getStatusCode(), e.getMessage());
            throw new RuntimeException("Erro ao obter coordenadas para a cidade", e);
        } catch (Exception e) {
            logger.error("Erro inesperado ao consumir a API de geocoding: {}", e.getMessage(), e);
            throw new RuntimeException("Erro ao obter coordenadas para a cidade", e);
        }
    }

    public RoteiroDeViagens criarRoteiro(String cidade, int dias) {
        double[] coordenadas = obterCoordenadasDaCidade(cidade);
        String latitudeStr = String.format("%.5f", coordenadas[0]);
        String longitudeStr = String.format("%.5f", coordenadas[1]);

        String coordenadasFormatadas = latitudeStr + "," + longitudeStr;

        try {
            String urlString = String.format(
                    "%s?q=tourist%%20attractions%%20%s&at=%s&apikey=%s",
                    ENDPOINT_DISCOVER, cidade, coordenadasFormatadas, apikey
            );


            URL url = new URL(urlString);

            System.out.println("URL: " + url.toString());
            System.out.println("Latitude: " + latitudeStr + ", Longitude: " + longitudeStr);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> resposta = restTemplate.getForEntity(url.toURI(), String.class);

            if (!resposta.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("Erro ao buscar atrações turísticas: " + resposta.getStatusCode());
            }

            try {
                JsonNode itemsNode = new ObjectMapper().readTree(resposta.getBody()).path("items");

                if (!itemsNode.isArray() || itemsNode.isEmpty()) {
                    throw new RuntimeException("Nenhuma atração turística encontrada para a cidade: " + cidade);
                }

                List<String> atividades = new ArrayList<>();
                itemsNode.forEach(item -> {
                    String titulo = item.path("title").asText(null);
                    if (titulo != null && !titulo.isEmpty()) atividades.add(titulo);
                });

                return dividirAtividadesEmDias(cidade, dias, atividades);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Erro ao processar a resposta JSON", e);
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar URL ou realizar requisição: " + e.getMessage(), e);
        }
    }


    private RoteiroDeViagens dividirAtividadesEmDias(String cidade, int dias, List<String> atividades) {
        List<RoteiroDeViagens.DiaRoteiro> roteiroDias = new ArrayList<>();
        int atividadesPorDia = atividades.size() / dias;
        int sobra = atividades.size() % dias;

        int index = 0;
        for (int i = 0; i < dias; i++) {
            int atividadesDiaCount = atividadesPorDia + (i < sobra ? 1 : 0);
            List<String> atividadesDia = new ArrayList<>(atividades.subList(index, index + atividadesDiaCount));
            index += atividadesDiaCount;

            roteiroDias.add(new RoteiroDeViagens.DiaRoteiro(i + 1, atividadesDia));
        }

        RoteiroDeViagens roteiro = new RoteiroDeViagens();
        roteiro.setDestination(cidade);
        roteiro.setDays(dias);
        roteiro.setRoteiro(roteiroDias);
        return roteiro;
    }
}

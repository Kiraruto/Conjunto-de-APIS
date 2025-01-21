package io.github.kiraruto.encurtadorurl.domain.clima.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.kiraruto.encurtadorurl.domain.clima.ApiClima;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class HttpClima {

    @Value("${climaReal.api.key}")
    private String keyReal;

    @Value("${climaReal.api.endpoint}")
    private String endpointReal;

    @Value("${climaHistorical.api.key}")
    private String keyHistorical;

    @Value("${climaHistorical.api.endpoint}")
    private String endpointHistorical;

    public ApiClima getWeatherReal(String city) {
        try {
            String urlString = String.format("%s?q=%s&appid=%s&units=metric&lang=pt&dt=%s",
                    endpointReal, city.replace(" ", "%20"), keyReal, LocalDateTime.now());
            System.out.println("URL da requisição: " + urlString);
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.toString());

                var cityName = rootNode.path("name").asText();
                var temperature = rootNode.path("main").path("temp").asDouble();
                var humidity = rootNode.path("main").path("humidity").asDouble();
                var wind = rootNode.path("wind").path("speed").asDouble();
                var description = rootNode.path("weather").get(0).path("description").asText();

                ApiClima apiClima = new ApiClima(cityName, temperature, humidity, wind, description);

                return apiClima;
            } else {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    errorResponse.append(errorLine);
                }
                errorReader.close();
                System.out.println("Resposta de erro: " + errorResponse);

                throw new RuntimeException("Erro ao obter dados da API: Código " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar com a API: " + e.getMessage());
        }
    }

    public ApiClima getWeatherHistorical(String city, LocalDate date) {
        try {
            String urlString = String.format("%s%s/%s?key=%s&lang=pt",
                    endpointHistorical, city.replace(" ", "%20"), date, keyHistorical);
            System.out.println("URL da requisição: " + urlString);


            System.out.println("URL da requisição: " + urlString);
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.toString());

                var cityName = rootNode.path("address").asText();
                var temperature = rootNode.path("days").get(0).path("temp").asDouble();
                var humidity = rootNode.path("days").get(0).path("humidity").asDouble();
                var wind = rootNode.path("days").get(0).path("windspeed").asDouble();
                var description = rootNode.path("days").get(0).path("description").asText();

                if (description.equalsIgnoreCase("")) {
                    description = "Os dados para a data " + date + " ainda não estão disponíveis.";
                }

                ApiClima apiClima = new ApiClima(cityName, temperature, humidity, wind, description, date);

                return apiClima;
            } else {
                BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                StringBuilder errorResponse = new StringBuilder();
                String errorLine;
                while ((errorLine = errorReader.readLine()) != null) {
                    errorResponse.append(errorLine);
                }
                errorReader.close();
                System.out.println("Resposta de erro: " + errorResponse);

                throw new RuntimeException("Erro ao obter dados da API: Código " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erro ao conectar com a API: " + e.getMessage());
        }
    }

}

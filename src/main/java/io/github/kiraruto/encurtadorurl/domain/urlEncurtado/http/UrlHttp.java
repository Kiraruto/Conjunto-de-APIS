package io.github.kiraruto.encurtadorurl.domain.urlEncurtado.http;

import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Configuration
public class UrlHttp {

    public String urlShortenerClient(String urlLonga) {
        HttpResponse<String> response = null;

        try {
            String endpoint = "https://api.encurtador.dev/encurtamentos";

            String jsonRequest = "{\"url\":\"" + urlLonga + "\"}";

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .header("Content-Type", "application/json")
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (Exception e) {
            e.printStackTrace();
            return "Erro ao enviar a solicitação para o encurtador de URL.";
        }
    }
}

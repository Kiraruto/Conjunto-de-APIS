package io.github.kiraruto.encurtadorurl.domain.clima;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "Clima")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ApiClima {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("cidade")
    private String city;

    @JsonProperty("temperatura")
    private double temperature;

    @JsonProperty("umidade")
    private double humidity;

    @JsonProperty("velocidadeVento")
    private double wind;

    @JsonProperty("descricao")
    private String description;

    @JsonProperty("data")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate date;

    public ApiClima(String city, double temperature, double humidity, double wind, String description, LocalDate formattedDate) {
        this.city = city;
        this.temperature = temperature;
        this.humidity = humidity;
        this.wind = wind;
        this.description = description;
        this.date = formattedDate;
    }

    public ApiClima(String cityName, double temperature, double humidity, double wind, String description) {
        this.city = cityName;
        this.temperature = temperature;
        this.humidity = humidity;
        this.wind = wind;
        this.description = description;
        this.date = LocalDate.now();
    }
}

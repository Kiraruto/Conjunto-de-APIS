package io.github.kiraruto.conjuntoDeAPIS.model.clima;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.kiraruto.conjuntoDeAPIS.model.users.User;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "Clima")
public class ApiClima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("cidade")
    private String city;

    @JsonProperty("temperatura")
    private Double temperature;

    @JsonProperty("umidade")
    private Double humidity;

    @JsonProperty("velocidadeVento")
    private Double wind;

    @JsonProperty("descricao")
    private String description;

    @JsonProperty("data")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ApiClima(Long id, User user, LocalDate date, String description, Double wind, Double temperature, String city, Double humidity) {
        this.id = id;
        this.user = user;
        this.date = date;
        this.description = description;
        this.wind = wind;
        this.temperature = temperature;
        this.city = city;
        this.humidity = humidity;
    }

    public ApiClima(String city, Double temperature, Double humidity, Double wind, String description, LocalDate formattedDate) {
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

    public ApiClima() {
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getWind() {
        return wind;
    }

    public void setWind(Double wind) {
        this.wind = wind;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

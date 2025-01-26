package io.github.kiraruto.encurtadorurl.domain.urlEncurtado;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "url_encurtado")
@Table(name = "url_encurtado")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UrlEncurtado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("longUrl")
    @Column(name = "long_url")
    private String longUrl;

    @JsonProperty("shortenedUrl")
    @Column(name = "shortened_url")
    private String shortenedUrl;

    public UrlEncurtado(String dtoUrlLong, String saveUrlShorten) {
        this.longUrl = dtoUrlLong;
        this.shortenedUrl = saveUrlShorten;
    }
}

package io.github.kiraruto.encurtadorurl.domain.urlEncurtado;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "url_encurtado")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UrlEncurtado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String longUrl;
    private String shortenedUrl;

    public UrlEncurtado(String dtoUrlLong, String saveUrlShorten) {
        this.longUrl = dtoUrlLong;
        this.shortenedUrl = saveUrlShorten;
    }
}

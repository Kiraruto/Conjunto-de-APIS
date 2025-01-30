package io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity(name = "url_encurtado")
@Table(name = "url_encurtado")
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

    public UrlEncurtado() {
    }

    public UrlEncurtado(Long id, String longUrl, String shortenedUrl) {
        this.id = id;
        this.longUrl = longUrl;
        this.shortenedUrl = shortenedUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortenedUrl() {
        return shortenedUrl;
    }

    public void setShortenedUrl(String shortenedUrl) {
        this.shortenedUrl = shortenedUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}


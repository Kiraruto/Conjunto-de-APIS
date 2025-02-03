package io.github.kiraruto.conjuntoDeAPIS.model.urlEncurtado;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.kiraruto.conjuntoDeAPIS.model.users.User;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

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

    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    public UrlEncurtado() {
    }

    public UrlEncurtado(Long id, String longUrl, String shortenedUrl, LocalDate createdAt, User user) {
        this.id = id;
        this.longUrl = longUrl;
        this.shortenedUrl = shortenedUrl;
        this.createdAt = createdAt;
        this.user = user;
    }

    public UrlEncurtado(Long save) {
        this.id = save;
    }

    public UrlEncurtado(String longUrl, String shortenedUrl, User user) {
        this.longUrl = longUrl;
        this.shortenedUrl = shortenedUrl;
        this.user = user;
        this.createdAt = LocalDate.now();
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}


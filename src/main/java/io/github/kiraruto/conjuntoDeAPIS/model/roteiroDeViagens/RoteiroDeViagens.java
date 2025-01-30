package io.github.kiraruto.conjuntoDeAPIS.model.roteiroDeViagens;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "roteiro_de_viagens")
@AllArgsConstructor
public class RoteiroDeViagens {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "destination", nullable = false)
    private String destination;

    @Column(name = "dias", nullable = false)
    private Integer days;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "roteiro_de_viagens_id")
    private List<DiaRoteiro> roteiro;

    public RoteiroDeViagens(String cidade, int dias, List<DiaRoteiro> roteiroDias) {
        this.destination = cidade;
        this.days = dias;
        this.roteiro = roteiroDias;
    }

    public RoteiroDeViagens() {
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public List<DiaRoteiro> getRoteiro() {
        return roteiro;
    }

    public void setRoteiro(List<DiaRoteiro> roteiro) {
        this.roteiro = roteiro;
    }

    @Override
    public String toString() {
        return "RoteiroDeViagens{" +
                "id=" + id +
                ", destination='" + destination + '\'' +
                ", days=" + days +
                ", roteiro=" + roteiro +
                '}';
    }
}

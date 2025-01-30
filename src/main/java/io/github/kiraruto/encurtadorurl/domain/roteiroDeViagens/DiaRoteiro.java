package io.github.kiraruto.encurtadorurl.domain.roteiroDeViagens;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "dias_roteiro")
public class DiaRoteiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dia", nullable = false)
    private int dia;

    @ElementCollection
    @CollectionTable(
            name = "atividades",
            joinColumns = @JoinColumn(name = "dia_roteiro_id")
    )
    @Column(name = "atividade", nullable = false)
    private List<String> atividades;

    public DiaRoteiro(int dia, List<String> atividades) {
        this.dia = dia;
        this.atividades = atividades;
    }

    public DiaRoteiro(Long id, int dia, List<String> atividades) {
        this.id = id;
        this.dia = dia;
        this.atividades = atividades;
    }

    public DiaRoteiro() {
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public List<String> getAtividades() {
        return atividades;
    }

    public void setAtividades(List<String> atividades) {
        this.atividades = atividades;
    }
}
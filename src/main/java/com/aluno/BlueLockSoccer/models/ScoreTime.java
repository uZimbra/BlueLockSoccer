package com.aluno.BlueLockSoccer.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import java.util.UUID;

@Entity
public class ScoreTime {

    public ScoreTime() {
        getAproveitamento();
    }

    @Id
    UUID id;

    @OneToOne
    @JoinColumn(name = "codigoDoTimeId")
    Time time;

    @Column
    Integer totalDePontos;

    @Column
    Integer totalDeJogos;

    @Column
    Integer totalDeVitorias;

    @Column
    Integer totalDeEmpates;

    @Column
    Integer totalDeDerrotas;

    @Column
    Integer golsPro;

    @Column
    Integer golsContra;

    @Column
    Integer saldoDeGols;

    @Transient
    String aproveitamento;

    @PostLoad
    public void getAproveitamento() {
        var result = (totalDeVitorias + totalDeDerrotas + totalDeEmpates) / 3;
        aproveitamento = new StringBuilder(result).append("%").toString();
    }
}

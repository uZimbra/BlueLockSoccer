package com.aluno.BlueLockSoccer.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class ScoreTime {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column
    Integer totalDePontos = 0;

    @Column
    Integer totalDeJogos = 0;

    @Column
    Integer totalDeVitorias = 0;

    @Column
    Integer totalDeEmpates = 0;

    @Column
    Integer totalDeDerrotas = 0;

    @Column
    Integer golsPro = 0;

    @Column
    Integer golsContra = 0;

    @Column
    Integer saldoDeGols = 0;

    @Column
    String aproveitamento = "0,00%";
}

package com.aluno.BlueLockSoccer.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ScoreTime {
    Integer totalDePontos;

    Integer totalDeJogos;

    Integer totalDeVitorias;

    Integer totalDeEmpates;

    Integer totalDeDerrotas;

    Integer golsPro;

    Integer golsContra;

    Integer saldoDeGols;

    String aproveitamento;
}

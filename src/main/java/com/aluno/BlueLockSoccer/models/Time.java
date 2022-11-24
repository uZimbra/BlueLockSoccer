package com.aluno.BlueLockSoccer.models;

import com.aluno.BlueLockSoccer.configuration.ApplicationContextProvider;
import com.aluno.BlueLockSoccer.services.ScoreTimeService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column
    String nome;

    @Transient
    ScoreTime scoreTime;
    public ScoreTime getScoreTime() {
        ScoreTimeService scoreTimeService = ApplicationContextProvider.bean(ScoreTimeService.class);

        var totalDePontos = scoreTimeService.getTotalDePontos(id);
        var totalDeJogos = scoreTimeService.getTotalDeJogos(id);
        var totalDeVitorias = scoreTimeService.getTotalDeVitorias(id);
        var totalDeEmpates = scoreTimeService.getTotalDeEmpates(id);
        var totalDeDerrotas = scoreTimeService.getTotalDeDerrotas(id);
        var totalDeGolsPro = scoreTimeService.getTotalDeGolsPro(id);
        var totalDeGolsContra = scoreTimeService.getTotalDeGolsContra(id);
        var totalDeSaldoDeGols = scoreTimeService.getTotalDeSaldoDeGols(id);
        var aproveitamento = scoreTimeService.getAproveitamento(totalDeVitorias, totalDeJogos, totalDeEmpates);
        var newScoreTime = new ScoreTime();
        newScoreTime.setTotalDePontos(totalDePontos);
        newScoreTime.setTotalDeJogos(totalDeJogos);
        newScoreTime.setTotalDeVitorias(totalDeVitorias);
        newScoreTime.setTotalDeEmpates(totalDeEmpates);
        newScoreTime.setTotalDeDerrotas(totalDeDerrotas);
        newScoreTime.setGolsPro(totalDeGolsPro);
        newScoreTime.setGolsContra(totalDeGolsContra);
        newScoreTime.setSaldoDeGols(totalDeSaldoDeGols);
        newScoreTime.setAproveitamento(aproveitamento);
        return newScoreTime;
    }
}

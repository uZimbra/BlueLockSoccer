package com.aluno.BlueLockSoccer.services;

import com.aluno.BlueLockSoccer.models.Partida;
import com.aluno.BlueLockSoccer.repositories.PartidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreTimeService {

    final private PartidaRepository partidaRepository;

    public int getTotalDePontos(Integer id) {
        var partidas = partidaRepository.findAllByTime1OrTime2(id);
        int totalDePontos = 0;
        for (Partida partida : partidas) {
            var scorePartida = partida.getScorePartida();
            if (partida.getTime1().getId().equals(id)) {
                var scoreTime1 = scorePartida.getScoreTime1();
                totalDePontos += scoreTime1;
            } else {
                var scoreTime2 = scorePartida.getScoreTime2();
                totalDePontos += scoreTime2;
            }
        }
        return totalDePontos;
    }

    public String getAproveitamento(int totalDeVitorias, int totalDeDerrotas, int totalDeEmpates) {
        var result = (totalDeVitorias + totalDeDerrotas + totalDeEmpates) / 3;
        return new StringBuilder(result).append("%").toString();
    }
}

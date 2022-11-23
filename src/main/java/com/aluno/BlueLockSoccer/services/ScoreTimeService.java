package com.aluno.BlueLockSoccer.services;

import com.aluno.BlueLockSoccer.models.Partida;
import com.aluno.BlueLockSoccer.repositories.PartidaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreTimeService {

    final private PartidaRepository partidaRepository;

    private static final int PONTOS_POR_PARTIDA_VENCIDA = 3;
    private static final int PONTOS_POR_PARTIDA_EMPATADA = 1;

    public int getTotalDePontos(Integer id) {
        //3 pontos para uma partida vencida;
        //1 pontos para um empate;
        //0 ponto por uma partida perdida;

        var partidas = partidaRepository.findAllByTime1OrTime2(id);
        int totalDePontos = 0;
        for (Partida partida : partidas) {
            var scorePartida = partida.getScorePartida();
            // Se for vítoria...
            if (partida.getTime1().getId().equals(id)) {
                var scoreTime1 = scorePartida.getScoreTime1();
                var scoreTime2 = scorePartida.getScoreTime2();
                if (scoreTime1 > scoreTime2) {
                    totalDePontos += PONTOS_POR_PARTIDA_VENCIDA;
                }
            } else {
                var scoreTime2 = scorePartida.getScoreTime2();
                var scoreTime1 = scorePartida.getScoreTime1();
                if (scoreTime2 > scoreTime1) {
                    totalDePontos += PONTOS_POR_PARTIDA_VENCIDA;
                }
            }
            // Se for empate...
            if (scorePartida.getScoreTime1() == scorePartida.getScoreTime2()) {
                totalDePontos += PONTOS_POR_PARTIDA_EMPATADA;
            }
        }
        return totalDePontos;
    }

    public int getTotalDeJogos(Integer id) {
        return partidaRepository.countByIdAllPartidas(id);
    }

    public int getTotalDeVitorias(Integer id) {
        var partidas = partidaRepository.findAllByTime1OrTime2(id);
        int totalDeVitorias = 0;
        for (Partida partida : partidas) {
            var scorePartida = partida.getScorePartida();
            if (partida.getTime1().getId().equals(id)) {
                var scoreTime1 = scorePartida.getScoreTime1();
                var scoreTime2 = scorePartida.getScoreTime2();
                if (scoreTime1 > scoreTime2) {
                    totalDeVitorias++;
                }
            } else {
                var scoreTime2 = scorePartida.getScoreTime2();
                var scoreTime1 = scorePartida.getScoreTime1();
                if (scoreTime2 > scoreTime1) {
                    totalDeVitorias++;
                }
            }
        }
        return totalDeVitorias;
    }

    public int getTotalDeEmpates(Integer id) {
        var partidas = partidaRepository.findAllByTime1OrTime2(id);
        int totalDeEmpates = 0;
        for (Partida partida : partidas) {
            var scorePartida = partida.getScorePartida();
            if (scorePartida.getScoreTime1() == scorePartida.getScoreTime2()) {
                totalDeEmpates++;
            }
        }
        return totalDeEmpates;
    }

    public int getTotalDeDerrotas(Integer id) {
        var partidas = partidaRepository.findAllByTime1OrTime2(id);
        int totalDeDerrotas = 0;
        for (Partida partida : partidas) {
            var scorePartida = partida.getScorePartida();
            if (partida.getTime1().getId().equals(id)) {
                var scoreTime1 = scorePartida.getScoreTime1();
                var scoreTime2 = scorePartida.getScoreTime2();
                if (scoreTime1 < scoreTime2) {
                    totalDeDerrotas++;
                }
            } else {
                var scoreTime2 = scorePartida.getScoreTime2();
                var scoreTime1 = scorePartida.getScoreTime1();
                if (scoreTime2 < scoreTime1) {
                    totalDeDerrotas++;
                }
            }
        }
        return totalDeDerrotas;
    }

    public int getTotalDeGolsPro(Integer id) {
        var partidas = partidaRepository.findAllByTime1OrTime2(id);
        int totalDeGolsPro = 0;
        for (Partida partida : partidas) {
            var scorePartida = partida.getScorePartida();
            if (partida.getTime1().getId().equals(id)) {
                totalDeGolsPro += scorePartida.getGolsProTime1();
            } else {
                totalDeGolsPro += scorePartida.getGolsProTime2();
            }
        }
        return totalDeGolsPro;
    }

    public int getTotalDeGolsContra(Integer id) {
        var partidas = partidaRepository.findAllByTime1OrTime2(id);
        int totalDeGolsContra = 0;
        for (Partida partida : partidas) {
            var scorePartida = partida.getScorePartida();
            if (partida.getTime1().getId().equals(id)) {
                totalDeGolsContra += scorePartida.getGolsContraTime1();
            } else {
                totalDeGolsContra += scorePartida.getGolsContraTime2();
            }
        }
        return totalDeGolsContra;
    }

    public int getTotalDeSaldoDeGols(Integer id) {
        var totalDeGolsPro = getTotalDeGolsPro(id);
        var totalDeGolsContra = getTotalDeGolsContra(id);
        return totalDeGolsPro - totalDeGolsContra;
    }

    public String getAproveitamento(int totalDeVitorias, int totalDeJogos, int totalDeEmpates) {
        double result = 0;
        double totalDePontosDisputados = totalDeJogos * PONTOS_POR_PARTIDA_VENCIDA;
        double pontosConquistadosPorVitoria = totalDeVitorias * PONTOS_POR_PARTIDA_VENCIDA;
        double pontosConquistadosPorEmpate = totalDeEmpates * PONTOS_POR_PARTIDA_EMPATADA;
        double totalDePontosConquistados = pontosConquistadosPorVitoria + pontosConquistadosPorEmpate;

        if (totalDePontosConquistados > 0 && totalDePontosDisputados > 0) {
            result = (totalDePontosConquistados / totalDePontosDisputados) * 100;
        }

        return String.format("%.2f%%", result);
    }
}

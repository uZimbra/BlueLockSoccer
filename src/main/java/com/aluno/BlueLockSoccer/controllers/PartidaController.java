package com.aluno.BlueLockSoccer.controllers;


import com.aluno.BlueLockSoccer.controllers.dtos.CreatePartidaDTO;
import com.aluno.BlueLockSoccer.models.Partida;
import com.aluno.BlueLockSoccer.models.ScorePartida;
import com.aluno.BlueLockSoccer.repositories.PartidaRepository;
import com.aluno.BlueLockSoccer.repositories.ScorePartidaRepository;
import com.aluno.BlueLockSoccer.repositories.TimeRepository;
import com.aluno.BlueLockSoccer.services.ScoreTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/partidas")
@RequiredArgsConstructor
public class PartidaController {

    final private PartidaRepository partidaRepository;
    final private TimeRepository timeRepository;
    final private ScorePartidaRepository scorePartidaRepository;

    final private ScoreTimeService scoreTimeService;


    @PostMapping
    public ResponseEntity<Partida> create(@RequestBody CreatePartidaDTO input) {
        try {
            var scorePartida = scorePartidaRepository.save(new ScorePartida());

            var time1 = timeRepository.findById(input.getTime1Id());
            var time2 = timeRepository.findById(input.getTime2Id());

            if (time1.isPresent() && time2.isPresent()) {
                var newPartida = new Partida();
                newPartida.setTime1(time1.get());
                newPartida.setTime2(time2.get());
                newPartida.setDataDaPartida(input.getDataDaPartida());
                newPartida.setScorePartida(scorePartida);

                var savedPartida = partidaRepository.save(newPartida);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedPartida);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        try {
            partidaRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Partida>> list() {
        var partida = partidaRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(partida);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Partida> findById(@PathVariable("id") Integer id) {
        var partida = partidaRepository.findById(id);
        if (partida.isPresent()) {
            return ResponseEntity.ok().body(partida.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Partida> update(@PathVariable("id") Integer id, @RequestBody Map<Object, Object> fields) {
        var partida = partidaRepository.findById(id);
        if (partida.isPresent()) {
            // alterando score da partida...
            ScorePartida scorePartidaDB = partida.get().getScorePartida();
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(ScorePartida.class, (String) key);
                assert field != null;
                if (!field.getName().equals("id")) {
                    field.setAccessible(true);
                }
                ReflectionUtils.setField(field, scorePartidaDB, value);
            });

            partida.get().setScorePartida(scorePartidaDB);

            var result = partidaRepository.save(partida.get());

            // alterando score do time...
            var time1Id = partida.get().getTime1().getId();
            var time2Id = partida.get().getTime2().getId();
            scoreTimeService.updateScoreTime(time1Id);
            scoreTimeService.updateScoreTime(time2Id);

            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}

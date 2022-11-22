package com.aluno.BlueLockSoccer.controllers;


import com.aluno.BlueLockSoccer.models.Partida;
import com.aluno.BlueLockSoccer.models.ScorePartida;
import com.aluno.BlueLockSoccer.repositories.PartidaRepository;
import com.aluno.BlueLockSoccer.repositories.ScorePartidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/partida")
public class PartidaController {

    @Autowired
    PartidaRepository partidaRepository;

    @Autowired
    ScorePartidaRepository scorePartidaRepository;

    @PostMapping
    public ResponseEntity<Partida> create(@RequestBody Partida partida) {
        try {
            var scorePartida = scorePartidaRepository.save(new ScorePartida(null, 0, 0));

            var newPartida = new Partida();
            newPartida.setScorePartida(scorePartida);
            newPartida.setCodigoDoTime1(partida.getCodigoDoTime1());
            newPartida.setCodigoDoTime2(partida.getCodigoDoTime2());
            newPartida.setDataDaPartida(partida.getDataDaPartida());

            var savedPartida = partidaRepository.save(newPartida);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedPartida);
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
        var notice = partidaRepository.findById(id);
        if (notice.isPresent()) {
            return ResponseEntity.ok().body(notice.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Optional<Partida>> update(@PathVariable("id") Integer id, @RequestBody Map<Object, Object> fields) {
        var partida = partidaRepository.findById(id);
        if (partida.isPresent()) {
            fields.forEach((key, value) -> {
                Field field = ReflectionUtils.findField(Partida.class, (String) key);
                assert field != null;
                if (!field.getName().equals("id")) {
                    field.setAccessible(true);
                }
                ReflectionUtils.setField(field, partida.get(), value);
            });

            var result = Optional.of(partidaRepository.save(partida.get()));

            return ResponseEntity.ok().body(result);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
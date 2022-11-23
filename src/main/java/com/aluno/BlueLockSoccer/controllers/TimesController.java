package com.aluno.BlueLockSoccer.controllers;


import com.aluno.BlueLockSoccer.controllers.dtos.CreateTimeDTO;
import com.aluno.BlueLockSoccer.models.ScoreTime;
import com.aluno.BlueLockSoccer.models.Time;
import com.aluno.BlueLockSoccer.repositories.TimeRepository;
import com.aluno.BlueLockSoccer.services.ScoreTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor
public class TimesController {

    final private TimeRepository timeRepository;
    final private ScoreTimeService scoreTimeService;

    @PostMapping
    public ResponseEntity<Time> create(@RequestBody CreateTimeDTO input) {
        try {
            var newTime = new Time();
            newTime.setNome(input.getNome());
            var savedTime = timeRepository.save(newTime);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTime);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") Integer id) {
        try {
            timeRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Time>> list() {
        var times = timeRepository.findAll();
        List<Time> result = times.stream().map(time -> {
            var totalDePontos = scoreTimeService.getTotalDePontos(time.getId());
            var scoreTime = new ScoreTime();
            scoreTime.setTotalDePontos(totalDePontos);
            time.setScoreTime(scoreTime);
            return time;
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

}

package com.aluno.BlueLockSoccer.controllers;


import com.aluno.BlueLockSoccer.controllers.dtos.CreateTimeDTO;
import com.aluno.BlueLockSoccer.models.ScoreTime;
import com.aluno.BlueLockSoccer.models.Time;
import com.aluno.BlueLockSoccer.repositories.ScoreTimeRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/times")
@RequiredArgsConstructor
public class TimesController {

    final private TimeRepository timeRepository;
    final private ScoreTimeRepository scoreTimeRepository;

    @PostMapping
    public ResponseEntity<Time> create(@RequestBody CreateTimeDTO input) {
        try {
            var newTime = new Time();
            var newScoreTime = scoreTimeRepository.save(new ScoreTime());
            newTime.setNome(input.getNome());
            newTime.setScoreTime(newScoreTime);
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

    @GetMapping("/{id}")
    public ResponseEntity<Time> findById(@PathVariable("id") Integer id) {
        Optional<Time> time = timeRepository.findById(id);
        if (time.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(time.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    @GetMapping
    public ResponseEntity<List<Time>> list() {
        var times = timeRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(times);
    }

}

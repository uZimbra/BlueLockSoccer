package com.aluno.BlueLockSoccer.controllers;


import com.aluno.BlueLockSoccer.models.Time;
import com.aluno.BlueLockSoccer.repositories.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/times")
public class TimesController {

    @Autowired
    TimeRepository timeRepository;

    @PostMapping
    public ResponseEntity<Time> create(@RequestBody Time time) {
        try {
            var savedTime = timeRepository.save(time);
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
        return ResponseEntity.status(HttpStatus.OK).body(times);
    }

}

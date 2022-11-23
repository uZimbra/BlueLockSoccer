package com.aluno.BlueLockSoccer.repositories;

import com.aluno.BlueLockSoccer.models.Partida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PartidaRepository extends JpaRepository<Partida, Integer> {
    @Query("FROM Partida p WHERE p.time1.id = ?1 OR p.time2.id = ?1")
    List<Partida> findAllByTime1OrTime2(Integer id);
}

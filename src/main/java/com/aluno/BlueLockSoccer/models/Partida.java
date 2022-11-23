package com.aluno.BlueLockSoccer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @OneToOne
    @JoinColumn(name = "time1Id")
    Time time1;

    @OneToOne
    @JoinColumn(name = "time2Id")
    Time time2;

    @Column
    LocalDate dataDaPartida;

    @OneToOne
    @JoinColumn(name = "scorePartidaId")
    ScorePartida scorePartida;

}

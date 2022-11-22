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

    @Column
    Integer codigoDoTime1;

    @Column
    Integer codigoDoTime2;

    @Column
    LocalDate dataDaPartida;

    @OneToOne
    @JoinColumn(name = "scorePartidaId")
    ScorePartida scorePartida;

}

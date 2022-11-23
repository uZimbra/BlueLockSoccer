package com.aluno.BlueLockSoccer.models;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
public class Time {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column
    String nome;

    @Transient
    ScoreTime scoreTime;
}

package com.aluno.BlueLockSoccer.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Column
    String nome;

    @OneToOne
    @JoinColumn(name = "scoreTimeId")
    ScoreTime scoreTime;
}

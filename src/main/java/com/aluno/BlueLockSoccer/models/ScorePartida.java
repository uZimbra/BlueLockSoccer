package com.aluno.BlueLockSoccer.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ScorePartida {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    @Transient
    Integer scoreTime1;
    public Integer getScoreTime1() {
        return golsProTime1 + golsContraTime2;
    }

    @Transient
    Integer scoreTime2;
    public Integer getScoreTime2() {
        return golsProTime2 + golsContraTime1;
    }

    @Column
    Integer golsProTime1 = 0;

    @Column
    Integer golsContraTime1 = 0;

    @Column
    Integer golsProTime2 = 0;

    @Column
    Integer golsContraTime2 = 0;

}

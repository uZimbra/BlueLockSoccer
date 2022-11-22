package com.aluno.BlueLockSoccer.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Data
@Entity
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer codigoDoTime;

    @Column
    String nome;

}

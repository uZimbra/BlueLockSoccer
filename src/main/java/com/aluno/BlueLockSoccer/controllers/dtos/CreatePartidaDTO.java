package com.aluno.BlueLockSoccer.controllers.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreatePartidaDTO {
    Integer time1Id;
    Integer time2Id;
    LocalDate dataDaPartida;
}

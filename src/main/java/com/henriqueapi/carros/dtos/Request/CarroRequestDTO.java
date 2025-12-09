package com.henriqueapi.carros.dtos.Request;

import com.henriqueapi.carros.entity.enums.StatusVeiculo;
import lombok.*;

@Data
public class CarroRequestDTO {
    private String nome;
    private String marca;
    private String cor;
    private int ano;
    private Long lojaId;
    private StatusVeiculo status;
}

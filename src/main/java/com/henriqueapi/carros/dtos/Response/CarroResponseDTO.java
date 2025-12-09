package com.henriqueapi.carros.dtos.Response;


import com.henriqueapi.carros.entity.enums.StatusVeiculo;
import lombok.*;

@Data
public class CarroResponseDTO {
    private Long id;
    private String nome;
    private String marca;
    private String cor;
    private int ano;
    private Long lojaId;
    private String lojaNome;
    private StatusVeiculo status;
}

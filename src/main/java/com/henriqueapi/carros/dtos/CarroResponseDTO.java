package com.henriqueapi.carros.dtos;


import lombok.*;

@Data
public class CarroResponseDTO {
    private Long id;
    private String nome;
    private String marca;
    private String cor;
    private int ano;
}

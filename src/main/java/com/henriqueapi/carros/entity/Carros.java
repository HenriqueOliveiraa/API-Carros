package com.henriqueapi.carros.entity;

import com.henriqueapi.carros.entity.enums.StatusVeiculo;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_carros")
@Data
public class Carros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String marca;
    private String cor;
    private int ano;

    @ManyToOne
    @JoinColumn(name = "lojas_id")
    private Lojas lojas;

    @Enumerated(EnumType.STRING)
    private StatusVeiculo status;

}
package com.henriqueapi.carros.dtos.Response;

import com.henriqueapi.carros.entity.enums.StatusVeiculo;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CarroResponseDTO {
    private Long id;
    private String nome;
    private String marca;
    private String cor;
    private Integer ano;
    private BigDecimal preco;
    private Integer quilometragem;
    private String combustivel;
    private String cambio;
    private String descricao;
    private Long lojaId;
    private String lojaNome;
    private StatusVeiculo status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}

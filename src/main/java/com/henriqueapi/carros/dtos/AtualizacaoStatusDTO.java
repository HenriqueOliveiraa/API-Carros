package com.henriqueapi.carros.dtos;

import com.henriqueapi.carros.entity.enums.StatusVeiculo;
import lombok.Data;

@Data
public class AtualizacaoStatusDTO {
    private StatusVeiculo novoStatus;
    private String observacao;
}


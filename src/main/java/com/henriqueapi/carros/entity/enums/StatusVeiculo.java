package com.henriqueapi.carros.entity.enums;

public enum StatusVeiculo {

    DISPONIVEL("Disponível"),
    VENDIDO("Vendido"),
    RESERVADO("Reservado"),
    EM_MANUTENCAO("Em manutenção");

    private String descricao;

    StatusVeiculo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

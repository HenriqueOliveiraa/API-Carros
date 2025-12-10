package com.henriqueapi.carros.entity.enums;

public enum TipoUsuario {

    ADMIN("Administrador do Sistema"),
    GERENTE("Gerente da loja"),
    VENDEDOR("Vendedor"),
    CLIENTE("Cliente");

    private String descricao;

    TipoUsuario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}

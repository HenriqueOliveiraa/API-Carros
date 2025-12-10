package com.henriqueapi.carros.dtos.Request;

import lombok.Data;

@Data
public class AlterarSenhaDTO {
    private String senhaAtual;
    private String novaSenha;
}

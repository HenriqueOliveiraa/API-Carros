package com.henriqueapi.carros.dtos.Request;

import lombok.Data;

@Data
public class LojaRequestDTO {

    private String nome;
    private String cnpj;
    private String endereco;
    private String cidade;
    private String estado;
    private String telefone;
}

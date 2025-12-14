package com.henriqueapi.carros.dtos.Request;

import lombok.Data;

@Data
public class RegistroRequestDTO {

    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String telefone;
}

package com.henriqueapi.carros.dtos.Response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LojaResponseDTO {

    private Long id;
    private String nome;
    private String cnpj;
    private String endereco;
    private String cidade;
    private String estado;
    private String telefone;
    private Boolean ativo;
    private Integer quantidadeVeiculos;
    private LocalDateTime dataCriacao;
}

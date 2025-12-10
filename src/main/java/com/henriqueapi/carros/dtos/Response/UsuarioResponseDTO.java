package com.henriqueapi.carros.dtos.Response;

import com.henriqueapi.carros.entity.enums.TipoUsuario;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private TipoUsuario tipo;
    private Long lojaId;
    private String lojaNome;
    private Boolean ativo;
    private LocalDateTime dataCriacao;
}

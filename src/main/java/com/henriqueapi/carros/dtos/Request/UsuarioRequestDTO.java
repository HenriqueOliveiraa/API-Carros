package com.henriqueapi.carros.dtos.Request;

import com.henriqueapi.carros.entity.enums.TipoUsuario;
import lombok.Data;

@Data
public class UsuarioRequestDTO {

    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String telefone;
    private TipoUsuario tipo;
    private Long lojaId;
}

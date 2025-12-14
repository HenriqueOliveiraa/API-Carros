package com.henriqueapi.carros.dtos.Response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.henriqueapi.carros.entity.enums.TipoUsuario;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginResponseDTO {

    private String token;
    private String tipo = "Bearer";
    private Long id;
    private String nome;
    private String email;
    private TipoUsuario tipoUsuario;
    private Long lojaId;
    private String lojaNome;
}

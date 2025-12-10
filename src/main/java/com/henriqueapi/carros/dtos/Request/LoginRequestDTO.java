package com.henriqueapi.carros.dtos.Request;


import lombok.Data;

@Data
public class LoginRequestDTO {

    private String email;
    private String senha;
}

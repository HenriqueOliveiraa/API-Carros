package com.henriqueapi.carros.dtos;

import lombok.Data;

@Data
public class TransferenciaCarroDTO {

    private Long carrosId;
    private Long lojaDestinoId;
    private String motivo;
}

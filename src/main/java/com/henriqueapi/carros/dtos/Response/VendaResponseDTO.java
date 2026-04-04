package com.henriqueapi.carros.dtos.Response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class VendaResponseDTO {

    private Long id;
    private Long carroId;
    private String carroNome;
    private String carroMarca;
    private Long vendedorId;
    private String vendedorNome;
    private String nomeCliente;
    private String cpfCliente;
    private String emailCliente;
    private String telefoneCliente;
    private BigDecimal valorVenda;
    private String observacoes;
    private LocalDateTime dataVenda;
}

package com.henriqueapi.carros.dtos.Request;

import com.henriqueapi.carros.entity.enums.StatusVeiculo;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarroRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
    private String nome;

    @NotBlank(message = "Marca é obrigatória")
    @Size(max = 100, message = "Marca deve ter no máximo 100 caracteres")
    private String marca;

    @Size(max = 50, message = "Cor deve ter no máximo 50 caracteres")
    private String cor;

    @NotNull(message = "Ano é obrigatório")
    @Min(value = 1886, message = "Ano inválido (o primeiro carro foi criado em 1886)")
    @Max(value = 2100, message = "Ano inválido")
    private Integer ano;

    @DecimalMin(value = "0.0", inclusive = false, message = "Preço deve ser maior que zero")
    @Digits(integer = 10, fraction = 2, message = "Preço inválido")
    private BigDecimal preco;

    @Min(value = 0, message = "Quilometragem não pode ser negativa")
    private Integer quilometragem;

    @Size(max = 30, message = "Combustível deve ter no máximo 30 caracteres")
    private String combustivel;

    @Size(max = 30, message = "Câmbio deve ter no máximo 30 caracteres")
    private String cambio;

    @Size(max = 2000, message = "Descrição deve ter no máximo 2000 caracteres")
    private String descricao;

    private Long lojaId;
    private StatusVeiculo status;
}

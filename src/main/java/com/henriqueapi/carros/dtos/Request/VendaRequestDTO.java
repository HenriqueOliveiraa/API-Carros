package com.henriqueapi.carros.dtos.Request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VendaRequestDTO {

    @NotNull(message = "ID do carro é obrigatório")
    private Long carroId;

    @NotBlank(message = "Nome do cliente é obrigatório")
    @Size(max = 150, message = "Nome deve ter no máximo 150 caracteres")
    private String nomeCliente;

    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF inválido (formato: 000.000.000-00)")
    private String cpfCliente;

    @Email(message = "Email do cliente inválido")
    private String emailCliente;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String telefoneCliente;

    @NotNull(message = "Valor da venda é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor da venda deve ser maior que zero")
    @Digits(integer = 10, fraction = 2, message = "Valor inválido")
    private BigDecimal valorVenda;

    @Size(max = 2000, message = "Observações devem ter no máximo 2000 caracteres")
    private String observacoes;
}

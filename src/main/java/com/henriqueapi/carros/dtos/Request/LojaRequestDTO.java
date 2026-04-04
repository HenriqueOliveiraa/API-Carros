package com.henriqueapi.carros.dtos.Request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class LojaRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 150, message = "Nome deve ter entre 2 e 150 caracteres")
    private String nome;

    @Pattern(regexp = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$", message = "CNPJ inválido (formato: 00.000.000/0000-00)")
    private String cnpj;

    @Size(max = 255, message = "Endereço deve ter no máximo 255 caracteres")
    private String endereco;

    @Size(max = 100, message = "Cidade deve ter no máximo 100 caracteres")
    private String cidade;

    @Size(min = 2, max = 2, message = "Estado deve ter 2 caracteres (ex: SP)")
    private String estado;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String telefone;
}

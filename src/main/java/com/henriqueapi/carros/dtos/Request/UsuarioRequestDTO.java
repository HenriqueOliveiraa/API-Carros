package com.henriqueapi.carros.dtos.Request;

import com.henriqueapi.carros.entity.enums.TipoUsuario;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UsuarioRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 150, message = "Nome deve ter entre 2 e 150 caracteres")
    private String nome;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "Senha deve conter letras maiúsculas, minúsculas, números e caracteres especiais"
    )
    private String senha;

    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "CPF inválido (formato: 000.000.000-00)")
    private String cpf;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    private String telefone;

    @NotNull(message = "Tipo de usuário é obrigatório")
    private TipoUsuario tipo;

    private Long lojaId;
}

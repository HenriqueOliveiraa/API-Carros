package com.henriqueapi.carros.entity;


import com.henriqueapi.carros.entity.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_usuarios")
@Data
public class Usuarios  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private String telefone;

    @Enumerated(EnumType.STRING)
    private TipoUsuario tipo;


    @ManyToOne
    @JoinColumn(name = "loja_id")
    private Lojas loja;

    private Boolean ativo = true;
    private LocalDateTime dataCriacao = LocalDateTime.now();
    private LocalDateTime dataAtualizacao;
}

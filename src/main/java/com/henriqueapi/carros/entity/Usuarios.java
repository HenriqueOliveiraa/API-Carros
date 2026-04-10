package com.henriqueapi.carros.entity;

import com.henriqueapi.carros.entity.enums.TipoUsuario;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tb_usuarios", indexes = {
        @Index(name = "idx_usuario_email", columnList = "email"),
        @Index(name = "idx_usuario_tipo", columnList = "tipo")
})
@Getter
@Setter
@NoArgsConstructor
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(unique = true, length = 14)
    private String cpf;

    @Column(length = 20)
    private String telefone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoUsuario tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "loja_id")
    private Lojas loja;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    private LocalDateTime dataAtualizacao;
}

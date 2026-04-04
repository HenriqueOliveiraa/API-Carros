package com.henriqueapi.carros.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_lojas", indexes = {
        @Index(name = "idx_loja_cnpj", columnList = "cnpj"),
        @Index(name = "idx_loja_cidade_estado", columnList = "cidade, estado")
})
@Getter
@Setter
@NoArgsConstructor
public class Lojas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(unique = true, length = 18)
    private String cnpj;

    @Column(length = 255)
    private String endereco;

    @Column(length = 100)
    private String cidade;

    @Column(length = 2)
    private String estado;

    @Column(length = 20)
    private String telefone;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    private LocalDateTime dataAtualizacao;

    @OneToMany(mappedBy = "lojas", fetch = FetchType.LAZY)
    private List<Carros> carros = new ArrayList<>();
}

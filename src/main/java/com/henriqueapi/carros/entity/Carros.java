package com.henriqueapi.carros.entity;

import com.henriqueapi.carros.entity.enums.StatusVeiculo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_carros", indexes = {
        @Index(name = "idx_carro_status", columnList = "status"),
        @Index(name = "idx_carro_loja", columnList = "lojas_id"),
        @Index(name = "idx_carro_marca", columnList = "marca")
})
@Getter
@Setter
@NoArgsConstructor
public class Carros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String nome;

    @Column(nullable = false, length = 100)
    private String marca;

    @Column(length = 50)
    private String cor;

    @Column(nullable = false)
    private Integer ano;

    @Column(precision = 12, scale = 2)
    private BigDecimal preco;

    private Integer quilometragem;

    @Column(length = 30)
    private String combustivel;

    @Column(length = 30)
    private String cambio;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lojas_id")
    private Lojas lojas;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private StatusVeiculo status = StatusVeiculo.DISPONIVEL;

    @Column(updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    private LocalDateTime dataAtualizacao;
}

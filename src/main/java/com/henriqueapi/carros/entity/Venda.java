package com.henriqueapi.carros.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_vendas", indexes = {
        @Index(name = "idx_venda_carro", columnList = "carro_id"),
        @Index(name = "idx_venda_vendedor", columnList = "vendedor_id"),
        @Index(name = "idx_venda_data", columnList = "dataVenda")
})
@Getter
@Setter
@NoArgsConstructor
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "carro_id", nullable = false)
    private Carros carro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id", nullable = false)
    private Usuarios vendedor;

    @Column(nullable = false, length = 150)
    private String nomeCliente;

    @Column(length = 14)
    private String cpfCliente;

    @Column(length = 150)
    private String emailCliente;

    @Column(length = 20)
    private String telefoneCliente;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valorVenda;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dataVenda = LocalDateTime.now();
}

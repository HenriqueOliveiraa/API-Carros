package com.henriqueapi.carros.entity;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "tb_lojas")
@Data
public class Lojas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cnpj;
    private String endereco;
    private String cidade;
    private String estado;
    private String telefone;

    @OneToMany(mappedBy = "lojas")
    private List<Carros> carros;
}

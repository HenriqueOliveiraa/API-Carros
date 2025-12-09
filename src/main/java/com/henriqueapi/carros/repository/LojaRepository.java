package com.henriqueapi.carros.repository;

import com.henriqueapi.carros.entity.Lojas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LojaRepository extends JpaRepository<Lojas, Long> {
    Optional<Lojas> findByCnpj(String cnpj);
    List<Lojas> findByCidadeAndEstado(String cidade, String estado);
    List<Lojas> findByEstado(String estado);
}

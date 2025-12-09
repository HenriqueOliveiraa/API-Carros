package com.henriqueapi.carros.repository;

import com.henriqueapi.carros.entity.Carros;
import com.henriqueapi.carros.entity.enums.StatusVeiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarroRepository extends JpaRepository<Carros, Long> {
    List<Carros> findByLojaId(Long lojaId);
    List<Carros> findByStatus(StatusVeiculo status);
    List<Carros> findByLojaIdAndStatus(Long lojaId, StatusVeiculo status);
}

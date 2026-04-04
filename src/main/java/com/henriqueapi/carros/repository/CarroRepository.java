package com.henriqueapi.carros.repository;

import com.henriqueapi.carros.entity.Carros;
import com.henriqueapi.carros.entity.enums.StatusVeiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarroRepository extends JpaRepository<Carros, Long> {

    Page<Carros> findByLojasId(Long lojaId, Pageable pageable);

    List<Carros> findByLojasId(Long lojaId);

    Page<Carros> findByStatus(StatusVeiculo status, Pageable pageable);

    List<Carros> findByLojasIdAndStatus(Long lojaId, StatusVeiculo status);

    @Query("SELECT COUNT(c) FROM Carros c WHERE c.lojas.id = :lojaId")
    long countByLojaId(@Param("lojaId") Long lojaId);
}

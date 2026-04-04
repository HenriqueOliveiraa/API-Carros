package com.henriqueapi.carros.repository;

import com.henriqueapi.carros.entity.Venda;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VendaRepository extends JpaRepository<Venda, Long> {

    Page<Venda> findByVendedorEmail(String email, Pageable pageable);

    @Query("SELECT v FROM Venda v WHERE v.carro.lojas.id = :lojaId")
    Page<Venda> findByLojaId(@Param("lojaId") Long lojaId, Pageable pageable);
}

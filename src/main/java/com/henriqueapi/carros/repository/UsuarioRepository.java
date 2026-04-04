package com.henriqueapi.carros.repository;

import com.henriqueapi.carros.entity.Usuarios;
import com.henriqueapi.carros.entity.enums.TipoUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuarios, Long> {

    Optional<Usuarios> findByEmail(String email);

    Optional<Usuarios> findByCpf(String cpf);

    Page<Usuarios> findByTipo(TipoUsuario tipo, Pageable pageable);

    Page<Usuarios> findByLojaId(Long lojaId, Pageable pageable);

    List<Usuarios> findByTipoAndLojaId(TipoUsuario tipo, Long lojaId);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);
}

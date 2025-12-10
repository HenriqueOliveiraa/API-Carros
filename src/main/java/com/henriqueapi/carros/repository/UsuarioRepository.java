package com.henriqueapi.carros.repository;

import com.henriqueapi.carros.entity.Usuarios;
import com.henriqueapi.carros.entity.enums.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository <Usuarios, Long> {

    Optional<Usuarios> findByEmail(String email);
    Optional<Usuarios> findByCpf(String cpf);
    List<Usuarios> findByTipo(TipoUsuario tipo);
    List<Usuarios> findByLojaId(Long lojaId);
    List<Usuarios> findByTipoAndLojaId(TipoUsuario tipo, Long lojaId);
    Boolean existsByEmail(String email);
    Boolean existsByCpf(String cpf);
}

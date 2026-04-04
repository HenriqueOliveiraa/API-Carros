package com.henriqueapi.carros.controller;

import com.henriqueapi.carros.dtos.Request.LojaRequestDTO;
import com.henriqueapi.carros.dtos.Response.CarroResponseDTO;
import com.henriqueapi.carros.dtos.Response.LojaResponseDTO;
import com.henriqueapi.carros.services.LojaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lojas")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Lojas", description = "Gestão de lojas")
public class LojasController {

    @Autowired
    private LojaService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cadastra nova loja")
    public ResponseEntity<LojaResponseDTO> criar(@Valid @RequestBody LojaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Lista todas as lojas com paginação")
    public ResponseEntity<Page<LojaResponseDTO>> listarTodas(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Busca loja por ID")
    public ResponseEntity<LojaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @Operation(summary = "Atualiza dados da loja")
    public ResponseEntity<LojaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody LojaRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remove loja")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/carros")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Lista carros de uma loja")
    public ResponseEntity<List<CarroResponseDTO>> listarCarrosDaLoja(@PathVariable Long id) {
        return ResponseEntity.ok(service.listarCarrosDaLoja(id));
    }
}

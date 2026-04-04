package com.henriqueapi.carros.controller;

import com.henriqueapi.carros.dtos.AtualizacaoStatusDTO;
import com.henriqueapi.carros.dtos.Request.CarroRequestDTO;
import com.henriqueapi.carros.dtos.Response.CarroResponseDTO;
import com.henriqueapi.carros.dtos.TransferenciaCarroDTO;
import com.henriqueapi.carros.entity.enums.StatusVeiculo;
import com.henriqueapi.carros.services.CarroService;
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
@RequestMapping("/api/carros")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Carros", description = "Gestão de veículos")
public class CarroController {

    @Autowired
    private CarroService service;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Lista todos os carros com paginação")
    public ResponseEntity<Page<CarroResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Busca carro por ID")
    public ResponseEntity<CarroResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @Operation(summary = "Cadastra novo carro")
    public ResponseEntity<CarroResponseDTO> create(@Valid @RequestBody CarroRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @Operation(summary = "Atualiza dados do carro")
    public ResponseEntity<CarroResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CarroRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remove carro")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/vincular-loja/{lojaId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @Operation(summary = "Vincula carro a uma loja")
    public ResponseEntity<CarroResponseDTO> vincularLoja(
            @PathVariable Long id,
            @PathVariable Long lojaId) {
        return ResponseEntity.ok(service.vincularLoja(id, lojaId));
    }

    @PutMapping("/{id}/transferir")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @Operation(summary = "Transfere carro para outra loja")
    public ResponseEntity<CarroResponseDTO> transferir(
            @PathVariable Long id,
            @Valid @RequestBody TransferenciaCarroDTO dto) {
        return ResponseEntity.ok(service.transferirVeiculo(id, dto));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @Operation(summary = "Atualiza status do veículo")
    public ResponseEntity<CarroResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @Valid @RequestBody AtualizacaoStatusDTO dto) {
        return ResponseEntity.ok(service.atualizarStatus(id, dto));
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Lista carros por status")
    public ResponseEntity<Page<CarroResponseDTO>> listarPorStatus(
            @PathVariable StatusVeiculo status,
            Pageable pageable) {
        return ResponseEntity.ok(service.listarPorStatus(status, pageable));
    }

    @GetMapping("/loja/{lojaId}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Lista carros de uma loja")
    public ResponseEntity<Page<CarroResponseDTO>> listarPorLoja(
            @PathVariable Long lojaId,
            Pageable pageable) {
        return ResponseEntity.ok(service.listarPorLoja(lojaId, pageable));
    }

    @GetMapping("/loja/{lojaId}/status/{status}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Lista carros de uma loja por status")
    public ResponseEntity<List<CarroResponseDTO>> listarPorLojaEStatus(
            @PathVariable Long lojaId,
            @PathVariable StatusVeiculo status) {
        return ResponseEntity.ok(service.listarPorLojaEStatus(lojaId, status));
    }
}

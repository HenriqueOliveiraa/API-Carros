package com.henriqueapi.carros.controller;

import com.henriqueapi.carros.dtos.Request.VendaRequestDTO;
import com.henriqueapi.carros.dtos.Response.VendaResponseDTO;
import com.henriqueapi.carros.services.VendaService;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/vendas")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Vendas", description = "Registro e consulta de vendas")
public class VendaController {

    @Autowired
    private VendaService service;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @Operation(summary = "Registra nova venda")
    public ResponseEntity<VendaResponseDTO> registrarVenda(
            @Valid @RequestBody VendaRequestDTO dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.registrarVenda(dto, userDetails.getUsername()));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @Operation(summary = "Lista todas as vendas com paginação")
    public ResponseEntity<Page<VendaResponseDTO>> listarTodas(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @Operation(summary = "Busca venda por ID")
    public ResponseEntity<VendaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/minha-vendas")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE', 'VENDEDOR')")
    @Operation(summary = "Lista vendas do vendedor logado")
    public ResponseEntity<Page<VendaResponseDTO>> minhasVendas(
            @AuthenticationPrincipal UserDetails userDetails,
            Pageable pageable) {
        return ResponseEntity.ok(service.findByVendedor(userDetails.getUsername(), pageable));
    }

    @GetMapping("/loja/{lojaId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @Operation(summary = "Lista vendas por loja")
    public ResponseEntity<Page<VendaResponseDTO>> listarPorLoja(
            @PathVariable Long lojaId,
            Pageable pageable) {
        return ResponseEntity.ok(service.findByLoja(lojaId, pageable));
    }
}

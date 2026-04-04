package com.henriqueapi.carros.controller;

import com.henriqueapi.carros.dtos.Request.AlterarSenhaDTO;
import com.henriqueapi.carros.dtos.Request.UsuarioRequestDTO;
import com.henriqueapi.carros.dtos.Response.UsuarioResponseDTO;
import com.henriqueapi.carros.entity.enums.TipoUsuario;
import com.henriqueapi.carros.services.UsuarioService;
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
@RequestMapping("/api/usuarios")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Usuários", description = "Gestão de usuários")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Cria novo usuário (apenas ADMIN)")
    public ResponseEntity<UsuarioResponseDTO> create(@Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lista todos os usuários com paginação")
    public ResponseEntity<Page<UsuarioResponseDTO>> listarTodos(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @authorizationService.isOwner(#id, authentication)")
    @Operation(summary = "Busca usuário por ID")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/tipo/{tipo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    @Operation(summary = "Lista usuários por tipo")
    public ResponseEntity<Page<UsuarioResponseDTO>> listarPorTipo(
            @PathVariable TipoUsuario tipo,
            Pageable pageable) {
        return ResponseEntity.ok(service.findByTipo(tipo, pageable));
    }

    @GetMapping("/loja/{lojaId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('GERENTE') and @authorizationService.isFromLoja(#lojaId, authentication))")
    @Operation(summary = "Lista usuários de uma loja")
    public ResponseEntity<Page<UsuarioResponseDTO>> listarPorLoja(
            @PathVariable Long lojaId,
            Pageable pageable) {
        return ResponseEntity.ok(service.findByLoja(lojaId, pageable));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Retorna dados do usuário logado")
    public ResponseEntity<UsuarioResponseDTO> meuPerfil(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(service.findByEmail(userDetails.getUsername()));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @authorizationService.isOwner(#id, authentication)")
    @Operation(summary = "Atualiza dados do usuário")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UsuarioRequestDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @PatchMapping("/{id}/ativo")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Ativa ou desativa usuário")
    public ResponseEntity<Void> ativarDesativar(
            @PathVariable Long id,
            @RequestParam Boolean ativo) {
        service.ativarDesativar(id, ativo);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/senha")
    @PreAuthorize("hasRole('ADMIN') or @authorizationService.isOwner(#id, authentication)")
    @Operation(summary = "Altera senha do usuário")
    public ResponseEntity<Void> alterarSenha(
            @PathVariable Long id,
            @Valid @RequestBody AlterarSenhaDTO dto) {
        service.alterarSenha(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remove usuário")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

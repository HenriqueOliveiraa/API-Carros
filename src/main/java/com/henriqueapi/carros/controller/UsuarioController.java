package com.henriqueapi.carros.controller;


import com.henriqueapi.carros.dtos.Request.AlterarSenhaDTO;
import com.henriqueapi.carros.dtos.Request.UsuarioRequestDTO;
import com.henriqueapi.carros.dtos.Response.UsuarioResponseDTO;
import com.henriqueapi.carros.entity.enums.TipoUsuario;
import com.henriqueapi.carros.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> create(@RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos() {
        List<UsuarioResponseDTO> usuarios = service.findAll();
        return ResponseEntity.ok(usuarios);
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @usuarioService.findById(#id).email == authentication.principal.username")
    public ResponseEntity<UsuarioResponseDTO> buscarPorId(@PathVariable Long id) {
        UsuarioResponseDTO usuario = service.findById(id);
        return ResponseEntity.ok(usuario);
    }


    @GetMapping("/tipo/{tipo}")
    @PreAuthorize("hasAnyRole('ADMIN', 'GERENTE')")
    public ResponseEntity<List<UsuarioResponseDTO>> listarPorTipo(@PathVariable TipoUsuario tipo) {
        List<UsuarioResponseDTO> usuarios = service.findByTipo(tipo);
        return ResponseEntity.ok(usuarios);
    }


    @GetMapping("/loja/{lojaId}")
    @PreAuthorize("hasRole('ADMIN') or (hasRole('GERENTE') and @usuarioService.findById(authentication.principal.id).lojaId == #lojaId)")
    public ResponseEntity<List<UsuarioResponseDTO>> listarPorLoja(@PathVariable Long lojaId) {
        List<UsuarioResponseDTO> usuarios = service.findByLoja(lojaId);
        return ResponseEntity.ok(usuarios);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or @usuarioService.findById(#id).email == authentication.principal.username")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody UsuarioRequestDTO dto) {
        UsuarioResponseDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }


    @PatchMapping("/{id}/ativo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> ativarDesativar(
            @PathVariable Long id,
            @RequestParam Boolean ativo) {
        service.ativarDesativar(id, ativo);
        return ResponseEntity.noContent().build();
    }


    @PatchMapping("/{id}/senha")
    @PreAuthorize("@usuarioService.findById(#id).email == authentication.principal.username")
    public ResponseEntity<Void> alterarSenha(
            @PathVariable Long id,
            @RequestBody AlterarSenhaDTO dto) {
        service.alterarSenha(id, dto);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

}

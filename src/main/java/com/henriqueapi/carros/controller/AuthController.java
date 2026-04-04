package com.henriqueapi.carros.controller;

import com.henriqueapi.carros.dtos.Request.LoginRequestDTO;
import com.henriqueapi.carros.dtos.Request.RegistroRequestDTO;
import com.henriqueapi.carros.dtos.Response.LoginResponseDTO;
import com.henriqueapi.carros.dtos.Response.UsuarioResponseDTO;
import com.henriqueapi.carros.services.AuthService;
import com.henriqueapi.carros.services.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticação", description = "Endpoints de login e registro")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    @Operation(summary = "Realiza login e retorna token JWT")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping("/registro")
    @Operation(summary = "Registra um novo cliente")
    public ResponseEntity<UsuarioResponseDTO> registrar(@Valid @RequestBody RegistroRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.registrar(dto));
    }
}

package com.henriqueapi.carros.controller;

import com.henriqueapi.carros.dtos.Request.LoginRequestDTO;
import com.henriqueapi.carros.dtos.Request.RegistroRequestDTO;
import com.henriqueapi.carros.dtos.Response.LoginResponseDTO;
import com.henriqueapi.carros.dtos.Response.UsuarioResponseDTO;
import com.henriqueapi.carros.services.AuthService;
import com.henriqueapi.carros.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO dto) {
        LoginResponseDTO response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registro")
    public ResponseEntity<UsuarioResponseDTO> registrar(@RequestBody RegistroRequestDTO dto) {
        UsuarioResponseDTO created = usuarioService.registrar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

}

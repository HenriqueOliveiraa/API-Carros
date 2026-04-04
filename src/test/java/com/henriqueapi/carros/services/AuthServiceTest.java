package com.henriqueapi.carros.services;

import com.henriqueapi.carros.dtos.Request.LoginRequestDTO;
import com.henriqueapi.carros.dtos.Response.LoginResponseDTO;
import com.henriqueapi.carros.entity.Usuarios;
import com.henriqueapi.carros.entity.enums.TipoUsuario;
import com.henriqueapi.carros.repository.UsuarioRepository;
import com.henriqueapi.carros.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtService jwtService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AuthService authService;

    private Usuarios usuario;
    private LoginRequestDTO loginRequest;

    @BeforeEach
    void setUp() {
        usuario = new Usuarios();
        usuario.setId(1L);
        usuario.setNome("Henrique");
        usuario.setEmail("henrique@admin.com");
        usuario.setSenha("senhaHash");
        usuario.setTipo(TipoUsuario.ADMIN);
        usuario.setAtivo(true);

        loginRequest = new LoginRequestDTO();
        loginRequest.setEmail("henrique@admin.com");
        loginRequest.setSenha("Admin@123");
    }

    @Test
    void login_comCredenciaisValidas_retornaToken() {
        when(authenticationManager.authenticate(any())).thenReturn(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha()));
        when(usuarioRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(usuario));
        when(jwtService.generateToken(usuario)).thenReturn("jwt-token");

        LoginResponseDTO response = authService.login(loginRequest);

        assertNotNull(response);
        assertEquals("jwt-token", response.getToken());
        assertEquals("henrique@admin.com", response.getEmail());
        assertEquals(TipoUsuario.ADMIN, response.getTipoUsuario());
    }

    @Test
    void login_comCredenciaisInvalidas_lancaExcecao() {
        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(Exception.class, () -> authService.login(loginRequest));
    }

    @Test
    void login_comUsuarioInativo_lancaExcecao() {
        usuario.setAtivo(false);
        when(authenticationManager.authenticate(any())).thenReturn(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getSenha()));
        when(usuarioRepository.findByEmail(loginRequest.getEmail())).thenReturn(Optional.of(usuario));

        Exception ex = assertThrows(Exception.class, () -> authService.login(loginRequest));
        assertTrue(ex.getMessage().contains("inativo"));
    }
}

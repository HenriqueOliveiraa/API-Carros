package com.henriqueapi.carros.security;

import com.henriqueapi.carros.entity.Lojas;
import com.henriqueapi.carros.entity.Usuarios;
import com.henriqueapi.carros.entity.enums.TipoUsuario;
import com.henriqueapi.carros.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthorizationServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthorizationService service;

    private Usuarios usuario;
    private Lojas loja;

    @BeforeEach
    void setUp() {
        loja = new Lojas();
        loja.setId(10L);
        loja.setNome("Loja Central");

        usuario = new Usuarios();
        usuario.setId(1L);
        usuario.setEmail("henrique@admin.com");
        usuario.setTipo(TipoUsuario.ADMIN);
        usuario.setLoja(loja);
        usuario.setAtivo(true);
    }

    @Test
    void isOwner_mesmoUsuario_retornaTrue() {
        when(authentication.getName()).thenReturn("henrique@admin.com");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        boolean result = service.isOwner(1L, authentication);

        assertTrue(result);
    }

    @Test
    void isOwner_outroUsuario_retornaFalse() {
        when(authentication.getName()).thenReturn("outro@email.com");
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));

        boolean result = service.isOwner(1L, authentication);

        assertFalse(result);
    }

    @Test
    void isOwner_idInexistente_retornaFalse() {
        when(authentication.getName()).thenReturn("henrique@admin.com");
        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        boolean result = service.isOwner(99L, authentication);

        assertFalse(result);
    }

    @Test
    void isFromLoja_mesmaLoja_retornaTrue() {
        when(authentication.getName()).thenReturn("henrique@admin.com");
        when(usuarioRepository.findByEmail("henrique@admin.com")).thenReturn(Optional.of(usuario));

        boolean result = service.isFromLoja(10L, authentication);

        assertTrue(result);
    }

    @Test
    void isFromLoja_lojasDiferentes_retornaFalse() {
        when(authentication.getName()).thenReturn("henrique@admin.com");
        when(usuarioRepository.findByEmail("henrique@admin.com")).thenReturn(Optional.of(usuario));

        boolean result = service.isFromLoja(99L, authentication);

        assertFalse(result);
    }

    @Test
    void isFromLoja_usuarioSemLoja_retornaFalse() {
        usuario.setLoja(null);
        when(authentication.getName()).thenReturn("henrique@admin.com");
        when(usuarioRepository.findByEmail("henrique@admin.com")).thenReturn(Optional.of(usuario));

        boolean result = service.isFromLoja(10L, authentication);

        assertFalse(result);
    }

    @Test
    void isFromLoja_emailInexistente_retornaFalse() {
        when(authentication.getName()).thenReturn("naoexiste@email.com");
        when(usuarioRepository.findByEmail("naoexiste@email.com")).thenReturn(Optional.empty());

        boolean result = service.isFromLoja(10L, authentication);

        assertFalse(result);
    }
}

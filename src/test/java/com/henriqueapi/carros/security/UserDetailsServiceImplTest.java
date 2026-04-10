package com.henriqueapi.carros.security;

import com.henriqueapi.carros.entity.Usuarios;
import com.henriqueapi.carros.entity.enums.TipoUsuario;
import com.henriqueapi.carros.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UsuarioRepository repository;

    @InjectMocks
    private UserDetailsServiceImpl service;

    private Usuarios usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuarios();
        usuario.setId(1L);
        usuario.setNome("Henrique");
        usuario.setEmail("henrique@admin.com");
        usuario.setSenha("$2a$10$hashSenha");
        usuario.setTipo(TipoUsuario.ADMIN);
        usuario.setAtivo(true);
    }

    @Test
    void loadUserByUsername_usuarioAtivo_retornaUserDetails() {
        when(repository.findByEmail("henrique@admin.com")).thenReturn(Optional.of(usuario));

        UserDetails userDetails = service.loadUserByUsername("henrique@admin.com");

        assertNotNull(userDetails);
        assertEquals("henrique@admin.com", userDetails.getUsername());
        assertEquals("$2a$10$hashSenha", userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.isAccountNonLocked());
    }

    @Test
    void loadUserByUsername_retornaRoleCorreta() {
        when(repository.findByEmail("henrique@admin.com")).thenReturn(Optional.of(usuario));

        UserDetails userDetails = service.loadUserByUsername("henrique@admin.com");

        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void loadUserByUsername_tipoGerente_retornaRoleGerente() {
        usuario.setTipo(TipoUsuario.GERENTE);
        when(repository.findByEmail("gerente@loja.com")).thenReturn(Optional.of(usuario));

        UserDetails userDetails = service.loadUserByUsername("gerente@loja.com");

        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_GERENTE")));
    }

    @Test
    void loadUserByUsername_usuarioInativo_retornaDesabilitado() {
        usuario.setAtivo(false);
        when(repository.findByEmail("henrique@admin.com")).thenReturn(Optional.of(usuario));

        UserDetails userDetails = service.loadUserByUsername("henrique@admin.com");

        assertFalse(userDetails.isEnabled());
        assertFalse(userDetails.isAccountNonLocked());
    }

    @Test
    void loadUserByUsername_usuarioInexistente_lancaExcecao() {
        when(repository.findByEmail("naoexiste@email.com")).thenReturn(Optional.empty());

        UsernameNotFoundException ex = assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("naoexiste@email.com"));

        assertTrue(ex.getMessage().contains("naoexiste@email.com"));
    }
}

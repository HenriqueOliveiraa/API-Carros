package com.henriqueapi.carros.services;

import com.henriqueapi.carros.dtos.Request.RegistroRequestDTO;
import com.henriqueapi.carros.dtos.Response.UsuarioResponseDTO;
import com.henriqueapi.carros.entity.Usuarios;
import com.henriqueapi.carros.entity.enums.TipoUsuario;
import com.henriqueapi.carros.exception.BusinessException;
import com.henriqueapi.carros.exception.ResourceNotFoundException;
import com.henriqueapi.carros.repository.LojaRepository;
import com.henriqueapi.carros.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository repository;

    @Mock
    private LojaRepository lojaRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService service;

    private Usuarios usuario;
    private RegistroRequestDTO registroRequest;

    @BeforeEach
    void setUp() {
        usuario = new Usuarios();
        usuario.setId(1L);
        usuario.setNome("João");
        usuario.setEmail("joao@email.com");
        usuario.setSenha("hashSenha");
        usuario.setTipo(TipoUsuario.CLIENTE);
        usuario.setAtivo(true);

        registroRequest = new RegistroRequestDTO();
        registroRequest.setNome("João");
        registroRequest.setEmail("joao@email.com");
        registroRequest.setSenha("Senha@123");
        registroRequest.setCpf("123.456.789-00");
    }

    @Test
    void registrar_comDadosValidos_criaUsuario() {
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.existsByCpf(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashSenha");
        when(repository.save(any())).thenReturn(usuario);

        UsuarioResponseDTO response = service.registrar(registroRequest);

        assertNotNull(response);
        assertEquals("João", response.getNome());
        assertEquals(TipoUsuario.CLIENTE, response.getTipo());
        verify(repository).save(any());
    }

    @Test
    void registrar_comEmailDuplicado_lancaExcecao() {
        when(repository.existsByEmail("joao@email.com")).thenReturn(true);

        assertThrows(BusinessException.class, () -> service.registrar(registroRequest));
        verify(repository, never()).save(any());
    }

    @Test
    void registrar_comCpfDuplicado_lancaExcecao() {
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.existsByCpf("123.456.789-00")).thenReturn(true);

        assertThrows(BusinessException.class, () -> service.registrar(registroRequest));
    }

    @Test
    void findById_existente_retornaUsuario() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));

        UsuarioResponseDTO response = service.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
    }

    @Test
    void findById_inexistente_lancaExcecao() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void ativarDesativar_desativaUsuario() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(repository.save(any())).thenReturn(usuario);

        service.ativarDesativar(1L, false);

        verify(repository).save(argThat(u -> !u.getAtivo()));
    }
}

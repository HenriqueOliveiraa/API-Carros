package com.henriqueapi.carros.services;

import com.henriqueapi.carros.dtos.Request.AlterarSenhaDTO;
import com.henriqueapi.carros.dtos.Request.RegistroRequestDTO;
import com.henriqueapi.carros.dtos.Request.UsuarioRequestDTO;
import com.henriqueapi.carros.dtos.Response.UsuarioResponseDTO;
import com.henriqueapi.carros.entity.Lojas;
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
    void registrar_sempreCriaComoCliente() {
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.existsByCpf(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashSenha");
        when(repository.save(any())).thenReturn(usuario);

        UsuarioResponseDTO response = service.registrar(registroRequest);

        assertEquals(TipoUsuario.CLIENTE, response.getTipo());
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
    void create_adminSemLoja_criaUsuario() {
        usuario.setTipo(TipoUsuario.ADMIN);
        UsuarioRequestDTO dto = criarUsuarioRequest(TipoUsuario.ADMIN, null);

        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.existsByCpf(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("hashSenha");
        when(repository.save(any())).thenReturn(usuario);

        UsuarioResponseDTO response = service.create(dto);

        assertNotNull(response);
        verify(repository).save(any());
    }

    @Test
    void create_vendedorSemLoja_lancaExcecao() {
        UsuarioRequestDTO dto = criarUsuarioRequest(TipoUsuario.VENDEDOR, null);
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.existsByCpf(anyString())).thenReturn(false);

        assertThrows(BusinessException.class, () -> service.create(dto));
    }

    @Test
    void create_gerenteSemLoja_lancaExcecao() {
        UsuarioRequestDTO dto = criarUsuarioRequest(TipoUsuario.GERENTE, null);
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.existsByCpf(anyString())).thenReturn(false);

        assertThrows(BusinessException.class, () -> service.create(dto));
    }

    @Test
    void create_vendedorComLoja_criaUsuario() {
        Lojas loja = new Lojas();
        loja.setId(1L);
        usuario.setTipo(TipoUsuario.VENDEDOR);
        usuario.setLoja(loja);

        UsuarioRequestDTO dto = criarUsuarioRequest(TipoUsuario.VENDEDOR, 1L);
        when(repository.existsByEmail(anyString())).thenReturn(false);
        when(repository.existsByCpf(anyString())).thenReturn(false);
        when(lojaRepository.findById(1L)).thenReturn(Optional.of(loja));
        when(passwordEncoder.encode(anyString())).thenReturn("hashSenha");
        when(repository.save(any())).thenReturn(usuario);

        UsuarioResponseDTO response = service.create(dto);

        assertNotNull(response);
        verify(lojaRepository).findById(1L);
    }

    @Test
    void create_comEmailDuplicado_lancaExcecao() {
        UsuarioRequestDTO dto = criarUsuarioRequest(TipoUsuario.CLIENTE, null);
        when(repository.existsByEmail(dto.getEmail())).thenReturn(true);

        assertThrows(BusinessException.class, () -> service.create(dto));
        verify(repository, never()).save(any());
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
    void findByEmail_existente_retornaUsuario() {
        when(repository.findByEmail("joao@email.com")).thenReturn(Optional.of(usuario));

        UsuarioResponseDTO response = service.findByEmail("joao@email.com");

        assertNotNull(response);
        assertEquals("joao@email.com", response.getEmail());
    }

    @Test
    void findByEmail_inexistente_lancaExcecao() {
        when(repository.findByEmail("naoexiste@email.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findByEmail("naoexiste@email.com"));
    }

    @Test
    void ativarDesativar_desativaUsuario() {
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(repository.save(any())).thenReturn(usuario);

        service.ativarDesativar(1L, false);

        verify(repository).save(argThat(u -> !u.getAtivo()));
    }

    @Test
    void ativarDesativar_ativaUsuario() {
        usuario.setAtivo(false);
        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(repository.save(any())).thenReturn(usuario);

        service.ativarDesativar(1L, true);

        verify(repository).save(argThat(Usuarios::getAtivo));
    }

    @Test
    void ativarDesativar_usuarioInexistente_lancaExcecao() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.ativarDesativar(99L, false));
    }

    @Test
    void alterarSenha_senhaAtualCorreta_altera() {
        AlterarSenhaDTO dto = new AlterarSenhaDTO();
        dto.setSenhaAtual("Senha@123");
        dto.setNovaSenha("NovaSenha@456");

        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("Senha@123", "hashSenha")).thenReturn(true);
        when(passwordEncoder.encode("NovaSenha@456")).thenReturn("novoHash");

        service.alterarSenha(1L, dto);

        verify(repository).save(argThat(u -> u.getSenha().equals("novoHash")));
    }

    @Test
    void alterarSenha_senhaAtualErrada_lancaExcecao() {
        AlterarSenhaDTO dto = new AlterarSenhaDTO();
        dto.setSenhaAtual("SenhaErrada");
        dto.setNovaSenha("NovaSenha@456");

        when(repository.findById(1L)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("SenhaErrada", "hashSenha")).thenReturn(false);

        assertThrows(BusinessException.class, () -> service.alterarSenha(1L, dto));
        verify(repository, never()).save(any());
    }

    @Test
    void delete_existente_remove() {
        when(repository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(repository).deleteById(1L);
    }

    @Test
    void delete_inexistente_lancaExcecao() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(99L));
        verify(repository, never()).deleteById(any());
    }

    private UsuarioRequestDTO criarUsuarioRequest(TipoUsuario tipo, Long lojaId) {
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setNome("Teste");
        dto.setEmail("teste@email.com");
        dto.setSenha("Senha@123");
        dto.setCpf("111.222.333-44");
        dto.setTipo(tipo);
        dto.setLojaId(lojaId);
        return dto;
    }
}

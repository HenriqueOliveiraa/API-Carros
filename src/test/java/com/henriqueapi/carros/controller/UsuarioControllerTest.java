package com.henriqueapi.carros.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henriqueapi.carros.dtos.Request.AlterarSenhaDTO;
import com.henriqueapi.carros.dtos.Request.UsuarioRequestDTO;
import com.henriqueapi.carros.dtos.Response.UsuarioResponseDTO;
import com.henriqueapi.carros.entity.enums.TipoUsuario;
import com.henriqueapi.carros.security.AuthorizationService;
import com.henriqueapi.carros.security.JwtService;
import com.henriqueapi.carros.security.SecurityConfig;
import com.henriqueapi.carros.security.UserDetailsServiceImpl;
import com.henriqueapi.carros.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UsuarioController.class)
@Import(SecurityConfig.class)
class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UsuarioService service;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @MockBean(name = "authorizationService")
    private AuthorizationService authorizationService;

    // ---- POST /api/usuarios ----

    @Test
    @WithMockUser(roles = "ADMIN")
    void create_comAdmin_retorna201() throws Exception {
        when(service.create(any())).thenReturn(criarUsuarioResponse());

        mockMvc.perform(post("/api/usuarios")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(criarUsuarioRequest())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João"));
    }

    @Test
    @WithMockUser(roles = "GERENTE")
    void create_comGerente_retorna403() throws Exception {
        mockMvc.perform(post("/api/usuarios")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(criarUsuarioRequest())))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void create_semNome_retorna400() throws Exception {
        UsuarioRequestDTO request = criarUsuarioRequest();
        request.setNome(null);

        mockMvc.perform(post("/api/usuarios")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // ---- GET /api/usuarios ----

    @Test
    @WithMockUser(roles = "ADMIN")
    void listarTodos_comAdmin_retorna200() throws Exception {
        Page<UsuarioResponseDTO> page = new PageImpl<>(List.of(criarUsuarioResponse()));
        when(service.findAll(any())).thenReturn(page);

        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nome").value("João"));
    }

    @Test
    @WithMockUser(roles = "GERENTE")
    void listarTodos_comGerente_retorna403() throws Exception {
        mockMvc.perform(get("/api/usuarios"))
                .andExpect(status().isForbidden());
    }

    // ---- GET /api/usuarios/{id} ----

    @Test
    @WithMockUser(roles = "ADMIN")
    void buscarPorId_comAdmin_retorna200() throws Exception {
        when(service.findById(1L)).thenReturn(criarUsuarioResponse());

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(roles = "CLIENTE", username = "joao@email.com")
    void buscarPorId_comProprioUsuario_retorna200() throws Exception {
        when(authorizationService.isOwner(eq(1L), any())).thenReturn(true);
        when(service.findById(1L)).thenReturn(criarUsuarioResponse());

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CLIENTE", username = "outro@email.com")
    void buscarPorId_comOutroUsuario_retorna403() throws Exception {
        when(authorizationService.isOwner(eq(1L), any())).thenReturn(false);

        mockMvc.perform(get("/api/usuarios/1"))
                .andExpect(status().isForbidden());
    }

    // ---- GET /api/usuarios/me ----

    @Test
    @WithMockUser(username = "joao@email.com")
    void meuPerfil_autenticado_retorna200() throws Exception {
        when(service.findByEmail("joao@email.com")).thenReturn(criarUsuarioResponse());

        mockMvc.perform(get("/api/usuarios/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("joao@email.com"));
    }

    @Test
    void meuPerfil_semAutenticacao_retorna403() throws Exception {
        mockMvc.perform(get("/api/usuarios/me"))
                .andExpect(status().isForbidden());
    }

    // ---- GET /api/usuarios/tipo/{tipo} ----

    @Test
    @WithMockUser(roles = "ADMIN")
    void listarPorTipo_comAdmin_retorna200() throws Exception {
        Page<UsuarioResponseDTO> page = new PageImpl<>(List.of(criarUsuarioResponse()));
        when(service.findByTipo(eq(TipoUsuario.CLIENTE), any())).thenReturn(page);

        mockMvc.perform(get("/api/usuarios/tipo/CLIENTE"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CLIENTE")
    void listarPorTipo_comCliente_retorna403() throws Exception {
        mockMvc.perform(get("/api/usuarios/tipo/CLIENTE"))
                .andExpect(status().isForbidden());
    }

    // ---- PUT /api/usuarios/{id} ----

    @Test
    @WithMockUser(roles = "ADMIN")
    void atualizar_comAdmin_retorna200() throws Exception {
        when(service.update(eq(1L), any())).thenReturn(criarUsuarioResponse());

        mockMvc.perform(put("/api/usuarios/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(criarUsuarioRequest())))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CLIENTE", username = "joao@email.com")
    void atualizar_comProprioUsuario_retorna200() throws Exception {
        when(authorizationService.isOwner(eq(1L), any())).thenReturn(true);
        when(service.update(eq(1L), any())).thenReturn(criarUsuarioResponse());

        mockMvc.perform(put("/api/usuarios/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(criarUsuarioRequest())))
                .andExpect(status().isOk());
    }

    // ---- PATCH /api/usuarios/{id}/ativo ----

    @Test
    @WithMockUser(roles = "ADMIN")
    void ativarDesativar_comAdmin_retorna204() throws Exception {
        doNothing().when(service).ativarDesativar(1L, false);

        mockMvc.perform(patch("/api/usuarios/1/ativo")
                        .with(csrf())
                        .param("ativo", "false"))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "GERENTE")
    void ativarDesativar_comGerente_retorna403() throws Exception {
        mockMvc.perform(patch("/api/usuarios/1/ativo")
                        .with(csrf())
                        .param("ativo", "false"))
                .andExpect(status().isForbidden());
    }

    // ---- PATCH /api/usuarios/{id}/senha ----

    @Test
    @WithMockUser(roles = "ADMIN")
    void alterarSenha_comAdmin_retorna204() throws Exception {
        AlterarSenhaDTO dto = new AlterarSenhaDTO();
        dto.setSenhaAtual("Senha@123");
        dto.setNovaSenha("NovaSenha@456");
        doNothing().when(service).alterarSenha(eq(1L), any());

        mockMvc.perform(patch("/api/usuarios/1/senha")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "CLIENTE", username = "joao@email.com")
    void alterarSenha_comProprioUsuario_retorna204() throws Exception {
        when(authorizationService.isOwner(eq(1L), any())).thenReturn(true);
        AlterarSenhaDTO dto = new AlterarSenhaDTO();
        dto.setSenhaAtual("Senha@123");
        dto.setNovaSenha("NovaSenha@456");
        doNothing().when(service).alterarSenha(eq(1L), any());

        mockMvc.perform(patch("/api/usuarios/1/senha")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isNoContent());
    }

    // ---- DELETE /api/usuarios/{id} ----

    @Test
    @WithMockUser(roles = "ADMIN")
    void deletar_comAdmin_retorna204() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/usuarios/1").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "GERENTE")
    void deletar_comGerente_retorna403() throws Exception {
        mockMvc.perform(delete("/api/usuarios/1").with(csrf()))
                .andExpect(status().isForbidden());
    }

    private UsuarioResponseDTO criarUsuarioResponse() {
        UsuarioResponseDTO r = new UsuarioResponseDTO();
        r.setId(1L);
        r.setNome("João");
        r.setEmail("joao@email.com");
        r.setTipo(TipoUsuario.CLIENTE);
        r.setAtivo(true);
        return r;
    }

    private UsuarioRequestDTO criarUsuarioRequest() {
        UsuarioRequestDTO dto = new UsuarioRequestDTO();
        dto.setNome("João");
        dto.setEmail("joao@email.com");
        dto.setSenha("Senha@123");
        dto.setTipo(TipoUsuario.CLIENTE);
        return dto;
    }
}

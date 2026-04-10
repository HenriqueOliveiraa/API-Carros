package com.henriqueapi.carros.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henriqueapi.carros.dtos.Request.LojaRequestDTO;
import com.henriqueapi.carros.dtos.Response.LojaResponseDTO;
import com.henriqueapi.carros.security.JwtService;
import com.henriqueapi.carros.security.SecurityConfig;
import com.henriqueapi.carros.security.UserDetailsServiceImpl;
import com.henriqueapi.carros.services.LojaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LojasController.class)
@Import(SecurityConfig.class)
class LojasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LojaService service;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @WithMockUser(roles = "ADMIN")
    void criar_comDadosValidos_retorna201() throws Exception {
        LojaRequestDTO request = new LojaRequestDTO();
        request.setNome("Auto Center SP");

        LojaResponseDTO response = new LojaResponseDTO();
        response.setId(1L);
        response.setNome("Auto Center SP");

        when(service.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/lojas")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Auto Center SP"));
    }

    @Test
    @WithMockUser(roles = "GERENTE")
    void criar_comRoleInsuficiente_retorna403() throws Exception {
        LojaRequestDTO request = new LojaRequestDTO();
        request.setNome("Auto Center SP");

        mockMvc.perform(post("/api/lojas")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void criar_semAutenticacao_retorna403() throws Exception {
        LojaRequestDTO request = new LojaRequestDTO();
        request.setNome("Auto Center SP");

        mockMvc.perform(post("/api/lojas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CLIENTE")
    void buscarPorId_autenticado_retorna200() throws Exception {
        LojaResponseDTO response = new LojaResponseDTO();
        response.setId(1L);
        response.setNome("Auto Center SP");

        when(service.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/lojas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Auto Center SP"));
    }

    @Test
    void buscarPorId_semAutenticacao_retorna403() throws Exception {
        mockMvc.perform(get("/api/lojas/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void atualizar_comAdmin_retorna200() throws Exception {
        LojaRequestDTO request = new LojaRequestDTO();
        request.setNome("Auto Center Atualizado");

        LojaResponseDTO response = new LojaResponseDTO();
        response.setId(1L);
        response.setNome("Auto Center Atualizado");

        when(service.update(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/lojas/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Auto Center Atualizado"));
    }

    @Test
    @WithMockUser(roles = "GERENTE")
    void atualizar_comGerente_retorna200() throws Exception {
        LojaRequestDTO request = new LojaRequestDTO();
        request.setNome("Auto Center Atualizado");

        LojaResponseDTO response = new LojaResponseDTO();
        response.setId(1L);
        response.setNome("Auto Center Atualizado");

        when(service.update(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/api/lojas/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CLIENTE")
    void atualizar_comCliente_retorna403() throws Exception {
        LojaRequestDTO request = new LojaRequestDTO();
        request.setNome("Auto Center Atualizado");

        mockMvc.perform(put("/api/lojas/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void deletar_comAdmin_retorna204() throws Exception {
        doNothing().when(service).delete(1L);

        mockMvc.perform(delete("/api/lojas/1").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "GERENTE")
    void deletar_comGerente_retorna403() throws Exception {
        mockMvc.perform(delete("/api/lojas/1").with(csrf()))
                .andExpect(status().isForbidden());
    }
}

package com.henriqueapi.carros.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henriqueapi.carros.dtos.Request.CarroRequestDTO;
import com.henriqueapi.carros.dtos.Response.CarroResponseDTO;
import com.henriqueapi.carros.entity.enums.StatusVeiculo;
import com.henriqueapi.carros.services.CarroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CarroController.class)
class CarroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CarroService service;

    @Test
    @WithMockUser(roles = "GERENTE")
    void create_comDadosValidos_retorna201() throws Exception {
        CarroRequestDTO request = new CarroRequestDTO();
        request.setNome("Civic");
        request.setMarca("Honda");
        request.setAno(2023);

        CarroResponseDTO response = new CarroResponseDTO();
        response.setId(1L);
        response.setNome("Civic");
        response.setMarca("Honda");
        response.setAno(2023);
        response.setStatus(StatusVeiculo.DISPONIVEL);

        when(service.create(any())).thenReturn(response);

        mockMvc.perform(post("/api/carros")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Civic"))
                .andExpect(jsonPath("$.status").value("DISPONIVEL"));
    }

    @Test
    @WithMockUser(roles = "GERENTE")
    void create_semNome_retorna400() throws Exception {
        CarroRequestDTO request = new CarroRequestDTO();
        request.setMarca("Honda");
        request.setAno(2023);

        mockMvc.perform(post("/api/carros")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_semAutenticacao_retorna403() throws Exception {
        CarroRequestDTO request = new CarroRequestDTO();
        request.setNome("Civic");
        request.setMarca("Honda");
        request.setAno(2023);

        mockMvc.perform(post("/api/carros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "CLIENTE")
    void create_comRoleInsuficiente_retorna403() throws Exception {
        CarroRequestDTO request = new CarroRequestDTO();
        request.setNome("Civic");
        request.setMarca("Honda");
        request.setAno(2023);

        mockMvc.perform(post("/api/carros")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }
}

package com.henriqueapi.carros.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henriqueapi.carros.dtos.Request.VendaRequestDTO;
import com.henriqueapi.carros.dtos.Response.VendaResponseDTO;
import com.henriqueapi.carros.security.JwtService;
import com.henriqueapi.carros.security.SecurityConfig;
import com.henriqueapi.carros.security.UserDetailsServiceImpl;
import com.henriqueapi.carros.services.VendaService;
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

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VendaController.class)
@Import(SecurityConfig.class)
class VendaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VendaService service;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @WithMockUser(roles = "VENDEDOR", username = "vendedor@loja.com")
    void registrarVenda_comVendedor_retorna201() throws Exception {
        VendaRequestDTO request = new VendaRequestDTO();
        request.setCarroId(1L);
        request.setNomeCliente("Carlos Cliente");
        request.setValorVenda(new BigDecimal("118000.00"));

        VendaResponseDTO response = new VendaResponseDTO();
        response.setId(1L);
        response.setCarroId(1L);
        response.setNomeCliente("Carlos Cliente");
        response.setValorVenda(new BigDecimal("118000.00"));

        when(service.registrarVenda(any(), anyString())).thenReturn(response);

        mockMvc.perform(post("/api/vendas")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nomeCliente").value("Carlos Cliente"))
                .andExpect(jsonPath("$.valorVenda").value(118000.00));
    }

    @Test
    @WithMockUser(roles = "CLIENTE")
    void registrarVenda_comCliente_retorna403() throws Exception {
        VendaRequestDTO request = new VendaRequestDTO();
        request.setCarroId(1L);
        request.setNomeCliente("Carlos Cliente");
        request.setValorVenda(new BigDecimal("118000.00"));

        mockMvc.perform(post("/api/vendas")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    void registrarVenda_semAutenticacao_retorna403() throws Exception {
        VendaRequestDTO request = new VendaRequestDTO();
        request.setCarroId(1L);
        request.setNomeCliente("Carlos Cliente");
        request.setValorVenda(new BigDecimal("118000.00"));

        mockMvc.perform(post("/api/vendas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "VENDEDOR")
    void registrarVenda_semCarroId_retorna400() throws Exception {
        VendaRequestDTO request = new VendaRequestDTO();
        request.setNomeCliente("Carlos Cliente");
        request.setValorVenda(new BigDecimal("118000.00"));

        mockMvc.perform(post("/api/vendas")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void listarTodas_comAdmin_retorna200() throws Exception {
        VendaResponseDTO venda = new VendaResponseDTO();
        venda.setId(1L);
        venda.setNomeCliente("Carlos Cliente");

        Page<VendaResponseDTO> page = new PageImpl<>(List.of(venda));
        when(service.findAll(any())).thenReturn(page);

        mockMvc.perform(get("/api/vendas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].nomeCliente").value("Carlos Cliente"));
    }

    @Test
    @WithMockUser(roles = "CLIENTE")
    void listarTodas_comCliente_retorna403() throws Exception {
        mockMvc.perform(get("/api/vendas"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "VENDEDOR", username = "vendedor@loja.com")
    void buscarPorId_comVendedor_retorna200() throws Exception {
        VendaResponseDTO response = new VendaResponseDTO();
        response.setId(1L);
        response.setNomeCliente("Carlos Cliente");

        when(service.findById(1L)).thenReturn(response);

        mockMvc.perform(get("/api/vendas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    @WithMockUser(roles = "VENDEDOR", username = "vendedor@loja.com")
    void minhasVendas_comVendedor_retorna200() throws Exception {
        Page<VendaResponseDTO> page = new PageImpl<>(List.of());
        when(service.findByVendedor(anyString(), any())).thenReturn(page);

        mockMvc.perform(get("/api/vendas/minha-vendas"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void listarPorLoja_comAdmin_retorna200() throws Exception {
        Page<VendaResponseDTO> page = new PageImpl<>(List.of());
        when(service.findByLoja(any(), any())).thenReturn(page);

        mockMvc.perform(get("/api/vendas/loja/1"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CLIENTE")
    void listarPorLoja_comCliente_retorna403() throws Exception {
        mockMvc.perform(get("/api/vendas/loja/1"))
                .andExpect(status().isForbidden());
    }
}

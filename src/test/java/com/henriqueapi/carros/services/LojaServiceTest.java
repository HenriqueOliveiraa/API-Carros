package com.henriqueapi.carros.services;

import com.henriqueapi.carros.dtos.Request.LojaRequestDTO;
import com.henriqueapi.carros.dtos.Response.CarroResponseDTO;
import com.henriqueapi.carros.dtos.Response.LojaResponseDTO;
import com.henriqueapi.carros.entity.Carros;
import com.henriqueapi.carros.entity.Lojas;
import com.henriqueapi.carros.entity.enums.StatusVeiculo;
import com.henriqueapi.carros.exception.BusinessException;
import com.henriqueapi.carros.exception.ResourceNotFoundException;
import com.henriqueapi.carros.repository.CarroRepository;
import com.henriqueapi.carros.repository.LojaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LojaServiceTest {

    @Mock
    private LojaRepository lojaRepository;

    @Mock
    private CarroRepository carroRepository;

    @InjectMocks
    private LojaService service;

    private Lojas loja;
    private LojaRequestDTO lojaRequest;

    @BeforeEach
    void setUp() {
        loja = new Lojas();
        loja.setId(1L);
        loja.setNome("Auto Center SP");
        loja.setCnpj("12.345.678/0001-90");
        loja.setCidade("São Paulo");
        loja.setEstado("SP");
        loja.setAtivo(true);

        lojaRequest = new LojaRequestDTO();
        lojaRequest.setNome("Auto Center SP");
        lojaRequest.setCnpj("12.345.678/0001-90");
        lojaRequest.setCidade("São Paulo");
        lojaRequest.setEstado("SP");
    }

    @Test
    void create_comCnpjUnico_criaLoja() {
        when(lojaRepository.findByCnpj(loja.getCnpj())).thenReturn(Optional.empty());
        when(lojaRepository.save(any())).thenReturn(loja);
        when(carroRepository.countByLojaId(anyLong())).thenReturn(0L);

        LojaResponseDTO response = service.create(lojaRequest);

        assertNotNull(response);
        assertEquals("Auto Center SP", response.getNome());
        verify(lojaRepository).save(any());
    }

    @Test
    void create_comCnpjDuplicado_lancaExcecao() {
        when(lojaRepository.findByCnpj(loja.getCnpj())).thenReturn(Optional.of(loja));

        assertThrows(BusinessException.class, () -> service.create(lojaRequest));
        verify(lojaRepository, never()).save(any());
    }

    @Test
    void create_semCnpj_criaLoja() {
        lojaRequest.setCnpj(null);
        loja.setCnpj(null);
        when(lojaRepository.save(any())).thenReturn(loja);
        when(carroRepository.countByLojaId(anyLong())).thenReturn(0L);

        LojaResponseDTO response = service.create(lojaRequest);

        assertNotNull(response);
        verify(lojaRepository, never()).findByCnpj(any());
    }

    @Test
    void update_lojaExistente_atualiza() {
        lojaRequest.setCnpj("99.999.999/0001-00");
        when(lojaRepository.findById(1L)).thenReturn(Optional.of(loja));
        when(lojaRepository.findByCnpj("99.999.999/0001-00")).thenReturn(Optional.empty());
        when(lojaRepository.save(any())).thenReturn(loja);
        when(carroRepository.countByLojaId(anyLong())).thenReturn(0L);

        LojaResponseDTO response = service.update(1L, lojaRequest);

        assertNotNull(response);
        verify(lojaRepository).save(any());
    }

    @Test
    void update_comMesmoCnpj_naoVerificaDuplicata() {
        when(lojaRepository.findById(1L)).thenReturn(Optional.of(loja));
        when(lojaRepository.save(any())).thenReturn(loja);
        when(carroRepository.countByLojaId(anyLong())).thenReturn(0L);

        LojaResponseDTO response = service.update(1L, lojaRequest);

        assertNotNull(response);
        verify(lojaRepository, never()).findByCnpj(any());
    }

    @Test
    void update_comNovoCnpjDuplicado_lancaExcecao() {
        Lojas outraLoja = new Lojas();
        outraLoja.setId(2L);
        outraLoja.setCnpj("99.999.999/0001-00");

        lojaRequest.setCnpj("99.999.999/0001-00");
        when(lojaRepository.findById(1L)).thenReturn(Optional.of(loja));
        when(lojaRepository.findByCnpj("99.999.999/0001-00")).thenReturn(Optional.of(outraLoja));

        assertThrows(BusinessException.class, () -> service.update(1L, lojaRequest));
    }

    @Test
    void update_lojaInexistente_lancaExcecao() {
        when(lojaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(99L, lojaRequest));
    }

    @Test
    void findById_existente_retornaLoja() {
        when(lojaRepository.findById(1L)).thenReturn(Optional.of(loja));
        when(carroRepository.countByLojaId(1L)).thenReturn(3L);

        LojaResponseDTO response = service.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Auto Center SP", response.getNome());
        assertEquals(3, response.getQuantidadeVeiculos());
    }

    @Test
    void findById_inexistente_lancaExcecao() {
        when(lojaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void findAll_retornaPaginado() {
        Page<Lojas> page = new PageImpl<>(List.of(loja));
        when(lojaRepository.findAll(any(PageRequest.class))).thenReturn(page);
        when(carroRepository.countByLojaId(anyLong())).thenReturn(2L);

        Page<LojaResponseDTO> result = service.findAll(PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        assertEquals(2, result.getContent().get(0).getQuantidadeVeiculos());
    }

    @Test
    void delete_existente_remove() {
        when(lojaRepository.existsById(1L)).thenReturn(true);

        service.delete(1L);

        verify(lojaRepository).deleteById(1L);
    }

    @Test
    void delete_inexistente_lancaExcecao() {
        when(lojaRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(99L));
        verify(lojaRepository, never()).deleteById(any());
    }

    @Test
    void listarCarrosDaLoja_lojaExistente_retornaCarros() {
        Carros carro = new Carros();
        carro.setId(1L);
        carro.setNome("Civic");
        carro.setMarca("Honda");
        carro.setAno(2023);
        carro.setStatus(StatusVeiculo.DISPONIVEL);
        carro.setPreco(new BigDecimal("120000.00"));

        when(lojaRepository.existsById(1L)).thenReturn(true);
        when(carroRepository.findByLojasId(1L)).thenReturn(List.of(carro));

        List<CarroResponseDTO> result = service.listarCarrosDaLoja(1L);

        assertEquals(1, result.size());
        assertEquals("Civic", result.get(0).getNome());
    }

    @Test
    void listarCarrosDaLoja_lojaInexistente_lancaExcecao() {
        when(lojaRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.listarCarrosDaLoja(99L));
    }

    @Test
    void listarCarrosDaLoja_semCarros_retornaListaVazia() {
        when(lojaRepository.existsById(1L)).thenReturn(true);
        when(carroRepository.findByLojasId(1L)).thenReturn(List.of());

        List<CarroResponseDTO> result = service.listarCarrosDaLoja(1L);

        assertTrue(result.isEmpty());
    }
}

package com.henriqueapi.carros.services;

import com.henriqueapi.carros.dtos.AtualizacaoStatusDTO;
import com.henriqueapi.carros.dtos.Request.CarroRequestDTO;
import com.henriqueapi.carros.dtos.Response.CarroResponseDTO;
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

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarroServiceTest {

    @Mock
    private CarroRepository repository;

    @Mock
    private LojaRepository lojasRepository;

    @InjectMocks
    private CarroService service;

    private Carros carro;
    private CarroRequestDTO carroRequest;

    @BeforeEach
    void setUp() {
        carro = new Carros();
        carro.setId(1L);
        carro.setNome("Civic");
        carro.setMarca("Honda");
        carro.setCor("Preto");
        carro.setAno(2023);
        carro.setPreco(new BigDecimal("120000.00"));
        carro.setStatus(StatusVeiculo.DISPONIVEL);

        carroRequest = new CarroRequestDTO();
        carroRequest.setNome("Civic");
        carroRequest.setMarca("Honda");
        carroRequest.setCor("Preto");
        carroRequest.setAno(2023);
        carroRequest.setPreco(new BigDecimal("120000.00"));
    }

    @Test
    void create_semLoja_criaCarro() {
        when(repository.save(any())).thenReturn(carro);

        CarroResponseDTO response = service.create(carroRequest);

        assertNotNull(response);
        assertEquals("Civic", response.getNome());
        assertEquals(StatusVeiculo.DISPONIVEL, response.getStatus());
        verify(repository).save(any());
    }

    @Test
    void create_comLojaInexistente_lancaExcecao() {
        carroRequest.setLojaId(99L);
        when(lojasRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.create(carroRequest));
    }

    @Test
    void findById_existente_retornaCarro() {
        when(repository.findById(1L)).thenReturn(Optional.of(carro));

        CarroResponseDTO response = service.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Honda", response.getMarca());
    }

    @Test
    void findById_inexistente_lancaExcecao() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void atualizarStatus_veiculoVendido_lancaExcecao() {
        carro.setStatus(StatusVeiculo.VENDIDO);
        when(repository.findById(1L)).thenReturn(Optional.of(carro));

        AtualizacaoStatusDTO dto = new AtualizacaoStatusDTO();
        dto.setNovoStatus(StatusVeiculo.DISPONIVEL);

        assertThrows(BusinessException.class, () -> service.atualizarStatus(1L, dto));
    }

    @Test
    void atualizarStatus_disponivelParaReservado_sucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(carro));
        when(repository.save(any())).thenReturn(carro);

        AtualizacaoStatusDTO dto = new AtualizacaoStatusDTO();
        dto.setNovoStatus(StatusVeiculo.RESERVADO);

        CarroResponseDTO response = service.atualizarStatus(1L, dto);
        assertNotNull(response);
    }

    @Test
    void delete_inexistente_lancaExcecao() {
        when(repository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(99L));
    }
}

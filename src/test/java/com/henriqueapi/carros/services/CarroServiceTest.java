package com.henriqueapi.carros.services;

import com.henriqueapi.carros.dtos.AtualizacaoStatusDTO;
import com.henriqueapi.carros.dtos.Request.CarroRequestDTO;
import com.henriqueapi.carros.dtos.Response.CarroResponseDTO;
import com.henriqueapi.carros.dtos.TransferenciaCarroDTO;
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
    void create_comLojaValida_vinculaLoja() {
        Lojas loja = new Lojas();
        loja.setId(1L);
        loja.setNome("Auto Center");
        carro.setLojas(loja);

        carroRequest.setLojaId(1L);
        when(lojasRepository.findById(1L)).thenReturn(Optional.of(loja));
        when(repository.save(any())).thenReturn(carro);

        CarroResponseDTO response = service.create(carroRequest);

        assertNotNull(response);
        assertEquals(1L, response.getLojaId());
        verify(lojasRepository).findById(1L);
    }

    @Test
    void create_comLojaInexistente_lancaExcecao() {
        carroRequest.setLojaId(99L);
        when(lojasRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.create(carroRequest));
    }

    @Test
    void create_statusCustomizado_usaStatusInformado() {
        carroRequest.setStatus(StatusVeiculo.RESERVADO);
        carro.setStatus(StatusVeiculo.RESERVADO);
        when(repository.save(any())).thenReturn(carro);

        CarroResponseDTO response = service.create(carroRequest);

        assertEquals(StatusVeiculo.RESERVADO, response.getStatus());
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
    void findAll_retornaPaginado() {
        Page<Carros> page = new PageImpl<>(List.of(carro));
        when(repository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<CarroResponseDTO> result = service.findAll(PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        assertEquals("Civic", result.getContent().get(0).getNome());
    }

    @Test
    void update_carroExistente_atualiza() {
        carroRequest.setNome("Civic Turbo");
        when(repository.findById(1L)).thenReturn(Optional.of(carro));
        when(repository.save(any())).thenReturn(carro);

        service.update(1L, carroRequest);

        verify(repository).save(any());
    }

    @Test
    void update_carroInexistente_lancaExcecao() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(99L, carroRequest));
    }

    @Test
    void update_comNovaLoja_atualizaVinculo() {
        Lojas novaLoja = new Lojas();
        novaLoja.setId(2L);
        novaLoja.setNome("Outra Loja");

        carroRequest.setLojaId(2L);
        when(repository.findById(1L)).thenReturn(Optional.of(carro));
        when(lojasRepository.findById(2L)).thenReturn(Optional.of(novaLoja));
        when(repository.save(any())).thenReturn(carro);

        service.update(1L, carroRequest);

        verify(lojasRepository).findById(2L);
        verify(repository).save(any());
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
    }

    @Test
    void vincularLoja_carroELojaExistentes_vincula() {
        Lojas loja = new Lojas();
        loja.setId(1L);
        loja.setNome("Auto Center");
        carro.setLojas(loja);

        when(repository.findById(1L)).thenReturn(Optional.of(carro));
        when(lojasRepository.findById(1L)).thenReturn(Optional.of(loja));
        when(repository.save(any())).thenReturn(carro);

        CarroResponseDTO response = service.vincularLoja(1L, 1L);

        assertNotNull(response);
        verify(repository).save(any());
    }

    @Test
    void vincularLoja_carroInexistente_lancaExcecao() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.vincularLoja(99L, 1L));
    }

    @Test
    void vincularLoja_lojaInexistente_lancaExcecao() {
        when(repository.findById(1L)).thenReturn(Optional.of(carro));
        when(lojasRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.vincularLoja(1L, 99L));
    }

    @Test
    void transferirVeiculo_carroVendido_lancaExcecao() {
        carro.setStatus(StatusVeiculo.VENDIDO);
        when(repository.findById(1L)).thenReturn(Optional.of(carro));

        TransferenciaCarroDTO dto = new TransferenciaCarroDTO();
        dto.setLojaDestinoId(2L);

        assertThrows(BusinessException.class, () -> service.transferirVeiculo(1L, dto));
    }

    @Test
    void transferirVeiculo_lojaDestinoInexistente_lancaExcecao() {
        when(repository.findById(1L)).thenReturn(Optional.of(carro));
        when(lojasRepository.findById(99L)).thenReturn(Optional.empty());

        TransferenciaCarroDTO dto = new TransferenciaCarroDTO();
        dto.setLojaDestinoId(99L);

        assertThrows(ResourceNotFoundException.class, () -> service.transferirVeiculo(1L, dto));
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
    void atualizarStatus_reservadoParaManutencao_lancaExcecao() {
        carro.setStatus(StatusVeiculo.RESERVADO);
        when(repository.findById(1L)).thenReturn(Optional.of(carro));

        AtualizacaoStatusDTO dto = new AtualizacaoStatusDTO();
        dto.setNovoStatus(StatusVeiculo.EM_MANUTENCAO);

        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.atualizarStatus(1L, dto));

        assertTrue(ex.getMessage().contains("reservado"));
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
    void atualizarStatus_disponivelParaManutencao_sucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(carro));
        when(repository.save(any())).thenReturn(carro);

        AtualizacaoStatusDTO dto = new AtualizacaoStatusDTO();
        dto.setNovoStatus(StatusVeiculo.EM_MANUTENCAO);

        CarroResponseDTO response = service.atualizarStatus(1L, dto);

        assertNotNull(response);
        verify(repository).save(any());
    }

    @Test
    void listarPorStatus_retornaCarros() {
        Page<Carros> page = new PageImpl<>(List.of(carro));
        when(repository.findByStatus(eq(StatusVeiculo.DISPONIVEL), any())).thenReturn(page);

        Page<CarroResponseDTO> result = service.listarPorStatus(StatusVeiculo.DISPONIVEL, PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        assertEquals(StatusVeiculo.DISPONIVEL, result.getContent().get(0).getStatus());
    }

    @Test
    void listarPorLoja_retornaCarrosDaLoja() {
        Page<Carros> page = new PageImpl<>(List.of(carro));
        when(repository.findByLojasId(eq(1L), any())).thenReturn(page);

        Page<CarroResponseDTO> result = service.listarPorLoja(1L, PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
    }

    @Test
    void listarPorLojaEStatus_retornaFiltrado() {
        when(repository.findByLojasIdAndStatus(1L, StatusVeiculo.DISPONIVEL)).thenReturn(List.of(carro));

        var result = service.listarPorLojaEStatus(1L, StatusVeiculo.DISPONIVEL);

        assertEquals(1, result.size());
    }
}

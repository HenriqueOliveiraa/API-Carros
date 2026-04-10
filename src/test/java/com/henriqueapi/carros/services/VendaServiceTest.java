package com.henriqueapi.carros.services;

import com.henriqueapi.carros.dtos.Request.VendaRequestDTO;
import com.henriqueapi.carros.dtos.Response.VendaResponseDTO;
import com.henriqueapi.carros.entity.Carros;
import com.henriqueapi.carros.entity.Usuarios;
import com.henriqueapi.carros.entity.Venda;
import com.henriqueapi.carros.entity.enums.StatusVeiculo;
import com.henriqueapi.carros.entity.enums.TipoUsuario;
import com.henriqueapi.carros.exception.BusinessException;
import com.henriqueapi.carros.exception.ResourceNotFoundException;
import com.henriqueapi.carros.repository.CarroRepository;
import com.henriqueapi.carros.repository.UsuarioRepository;
import com.henriqueapi.carros.repository.VendaRepository;
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
class VendaServiceTest {

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private CarroRepository carroRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private VendaService service;

    private Carros carro;
    private Usuarios vendedor;
    private Venda venda;
    private VendaRequestDTO vendaRequest;

    @BeforeEach
    void setUp() {
        carro = new Carros();
        carro.setId(1L);
        carro.setNome("Civic");
        carro.setMarca("Honda");
        carro.setAno(2023);
        carro.setStatus(StatusVeiculo.DISPONIVEL);

        vendedor = new Usuarios();
        vendedor.setId(1L);
        vendedor.setNome("João Vendedor");
        vendedor.setEmail("joao@loja.com");
        vendedor.setTipo(TipoUsuario.VENDEDOR);
        vendedor.setAtivo(true);

        venda = new Venda();
        venda.setId(1L);
        venda.setCarro(carro);
        venda.setVendedor(vendedor);
        venda.setNomeCliente("Carlos Cliente");
        venda.setCpfCliente("111.222.333-44");
        venda.setEmailCliente("carlos@email.com");
        venda.setValorVenda(new BigDecimal("118000.00"));

        vendaRequest = new VendaRequestDTO();
        vendaRequest.setCarroId(1L);
        vendaRequest.setNomeCliente("Carlos Cliente");
        vendaRequest.setCpfCliente("111.222.333-44");
        vendaRequest.setEmailCliente("carlos@email.com");
        vendaRequest.setValorVenda(new BigDecimal("118000.00"));
    }

    @Test
    void registrarVenda_carroDisponivel_sucesso() {
        when(carroRepository.findById(1L)).thenReturn(Optional.of(carro));
        when(usuarioRepository.findByEmail("joao@loja.com")).thenReturn(Optional.of(vendedor));
        when(carroRepository.save(any())).thenReturn(carro);
        when(vendaRepository.save(any())).thenReturn(venda);

        VendaResponseDTO response = service.registrarVenda(vendaRequest, "joao@loja.com");

        assertNotNull(response);
        assertEquals("Carlos Cliente", response.getNomeCliente());
        assertEquals(new BigDecimal("118000.00"), response.getValorVenda());
        verify(carroRepository).save(argThat(c -> c.getStatus() == StatusVeiculo.VENDIDO));
    }

    @Test
    void registrarVenda_carroReservado_sucesso() {
        carro.setStatus(StatusVeiculo.RESERVADO);
        when(carroRepository.findById(1L)).thenReturn(Optional.of(carro));
        when(usuarioRepository.findByEmail("joao@loja.com")).thenReturn(Optional.of(vendedor));
        when(carroRepository.save(any())).thenReturn(carro);
        when(vendaRepository.save(any())).thenReturn(venda);

        VendaResponseDTO response = service.registrarVenda(vendaRequest, "joao@loja.com");

        assertNotNull(response);
        verify(carroRepository).save(argThat(c -> c.getStatus() == StatusVeiculo.VENDIDO));
    }

    @Test
    void registrarVenda_carroJaVendido_lancaExcecao() {
        carro.setStatus(StatusVeiculo.VENDIDO);
        when(carroRepository.findById(1L)).thenReturn(Optional.of(carro));

        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.registrarVenda(vendaRequest, "joao@loja.com"));

        assertTrue(ex.getMessage().contains("já foi vendido"));
        verify(vendaRepository, never()).save(any());
    }

    @Test
    void registrarVenda_carroEmManutencao_lancaExcecao() {
        carro.setStatus(StatusVeiculo.EM_MANUTENCAO);
        when(carroRepository.findById(1L)).thenReturn(Optional.of(carro));

        BusinessException ex = assertThrows(BusinessException.class,
                () -> service.registrarVenda(vendaRequest, "joao@loja.com"));

        assertTrue(ex.getMessage().contains("manutenção"));
        verify(vendaRepository, never()).save(any());
    }

    @Test
    void registrarVenda_carroInexistente_lancaExcecao() {
        when(carroRepository.findById(99L)).thenReturn(Optional.empty());
        vendaRequest.setCarroId(99L);

        assertThrows(ResourceNotFoundException.class,
                () -> service.registrarVenda(vendaRequest, "joao@loja.com"));
    }

    @Test
    void registrarVenda_vendedorInexistente_lancaExcecao() {
        when(carroRepository.findById(1L)).thenReturn(Optional.of(carro));
        when(usuarioRepository.findByEmail("inexistente@loja.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.registrarVenda(vendaRequest, "inexistente@loja.com"));
    }

    @Test
    void registrarVenda_marcaStatusVendidoNoCarro() {
        when(carroRepository.findById(1L)).thenReturn(Optional.of(carro));
        when(usuarioRepository.findByEmail("joao@loja.com")).thenReturn(Optional.of(vendedor));
        when(carroRepository.save(any())).thenReturn(carro);
        when(vendaRepository.save(any())).thenReturn(venda);

        service.registrarVenda(vendaRequest, "joao@loja.com");

        assertEquals(StatusVeiculo.VENDIDO, carro.getStatus());
    }

    @Test
    void findById_existente_retornaVenda() {
        when(vendaRepository.findById(1L)).thenReturn(Optional.of(venda));

        VendaResponseDTO response = service.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Carlos Cliente", response.getNomeCliente());
    }

    @Test
    void findById_inexistente_lancaExcecao() {
        when(vendaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void findAll_retornaPaginado() {
        Page<Venda> page = new PageImpl<>(List.of(venda));
        when(vendaRepository.findAll(any(PageRequest.class))).thenReturn(page);

        Page<VendaResponseDTO> result = service.findAll(PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        assertEquals("Carlos Cliente", result.getContent().get(0).getNomeCliente());
    }

    @Test
    void findByVendedor_retornaPaginado() {
        Page<Venda> page = new PageImpl<>(List.of(venda));
        when(vendaRepository.findByVendedorEmail(eq("joao@loja.com"), any())).thenReturn(page);

        Page<VendaResponseDTO> result = service.findByVendedor("joao@loja.com", PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
        assertEquals("João Vendedor", result.getContent().get(0).getVendedorNome());
    }

    @Test
    void findByLoja_retornaPaginado() {
        Page<Venda> page = new PageImpl<>(List.of(venda));
        when(vendaRepository.findByLojaId(eq(1L), any())).thenReturn(page);

        Page<VendaResponseDTO> result = service.findByLoja(1L, PageRequest.of(0, 10));

        assertEquals(1, result.getTotalElements());
    }
}

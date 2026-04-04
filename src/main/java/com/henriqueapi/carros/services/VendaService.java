package com.henriqueapi.carros.services;

import com.henriqueapi.carros.dtos.Request.VendaRequestDTO;
import com.henriqueapi.carros.dtos.Response.VendaResponseDTO;
import com.henriqueapi.carros.entity.Carros;
import com.henriqueapi.carros.entity.Usuarios;
import com.henriqueapi.carros.entity.Venda;
import com.henriqueapi.carros.entity.enums.StatusVeiculo;
import com.henriqueapi.carros.exception.BusinessException;
import com.henriqueapi.carros.exception.ResourceNotFoundException;
import com.henriqueapi.carros.repository.CarroRepository;
import com.henriqueapi.carros.repository.UsuarioRepository;
import com.henriqueapi.carros.repository.VendaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public VendaResponseDTO registrarVenda(VendaRequestDTO dto, String emailVendedor) {
        Carros carro = carroRepository.findById(dto.getCarroId())
                .orElseThrow(() -> new ResourceNotFoundException("Carro não encontrado"));

        if (carro.getStatus() == StatusVeiculo.VENDIDO) {
            throw new BusinessException("Veículo já foi vendido");
        }
        if (carro.getStatus() == StatusVeiculo.EM_MANUTENCAO) {
            throw new BusinessException("Veículo em manutenção não pode ser vendido");
        }

        Usuarios vendedor = usuarioRepository.findByEmail(emailVendedor)
                .orElseThrow(() -> new ResourceNotFoundException("Vendedor não encontrado"));

        Venda venda = new Venda();
        venda.setCarro(carro);
        venda.setVendedor(vendedor);
        venda.setNomeCliente(dto.getNomeCliente());
        venda.setCpfCliente(dto.getCpfCliente());
        venda.setEmailCliente(dto.getEmailCliente());
        venda.setTelefoneCliente(dto.getTelefoneCliente());
        venda.setValorVenda(dto.getValorVenda());
        venda.setObservacoes(dto.getObservacoes());

        carro.setStatus(StatusVeiculo.VENDIDO);
        carroRepository.save(carro);

        return mapToResponseDTO(vendaRepository.save(venda));
    }

    public Page<VendaResponseDTO> findAll(Pageable pageable) {
        return vendaRepository.findAll(pageable).map(this::mapToResponseDTO);
    }

    public VendaResponseDTO findById(Long id) {
        return vendaRepository.findById(id)
                .map(this::mapToResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Venda não encontrada"));
    }

    public Page<VendaResponseDTO> findByVendedor(String email, Pageable pageable) {
        return vendaRepository.findByVendedorEmail(email, pageable).map(this::mapToResponseDTO);
    }

    public Page<VendaResponseDTO> findByLoja(Long lojaId, Pageable pageable) {
        return vendaRepository.findByLojaId(lojaId, pageable).map(this::mapToResponseDTO);
    }

    private VendaResponseDTO mapToResponseDTO(Venda venda) {
        VendaResponseDTO dto = new VendaResponseDTO();
        dto.setId(venda.getId());
        dto.setCarroId(venda.getCarro().getId());
        dto.setCarroNome(venda.getCarro().getNome());
        dto.setCarroMarca(venda.getCarro().getMarca());
        dto.setVendedorId(venda.getVendedor().getId());
        dto.setVendedorNome(venda.getVendedor().getNome());
        dto.setNomeCliente(venda.getNomeCliente());
        dto.setCpfCliente(venda.getCpfCliente());
        dto.setEmailCliente(venda.getEmailCliente());
        dto.setTelefoneCliente(venda.getTelefoneCliente());
        dto.setValorVenda(venda.getValorVenda());
        dto.setObservacoes(venda.getObservacoes());
        dto.setDataVenda(venda.getDataVenda());
        return dto;
    }
}

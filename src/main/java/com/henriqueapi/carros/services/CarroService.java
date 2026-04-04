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
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroRepository repository;

    @Autowired
    private LojaRepository lojasRepository;

    @Transactional
    public CarroResponseDTO create(CarroRequestDTO dto) {
        Carros carro = new Carros();
        mapRequestToEntity(dto, carro);
        carro.setStatus(dto.getStatus() != null ? dto.getStatus() : StatusVeiculo.DISPONIVEL);

        if (dto.getLojaId() != null) {
            Lojas loja = lojasRepository.findById(dto.getLojaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Loja não encontrada"));
            carro.setLojas(loja);
        }

        return mapToResponseDTO(repository.save(carro));
    }

    @Transactional
    public CarroResponseDTO update(Long id, CarroRequestDTO dto) {
        Carros carro = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Carro com ID " + id + " não encontrado"));

        mapRequestToEntity(dto, carro);
        carro.setDataAtualizacao(LocalDateTime.now());

        if (dto.getStatus() != null) {
            validarTransicaoStatus(carro.getStatus(), dto.getStatus());
            carro.setStatus(dto.getStatus());
        }
        if (dto.getLojaId() != null) {
            Lojas loja = lojasRepository.findById(dto.getLojaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Loja não encontrada"));
            carro.setLojas(loja);
        }

        return mapToResponseDTO(repository.save(carro));
    }

    public Page<CarroResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::mapToResponseDTO);
    }

    public CarroResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(this::mapToResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Carro com ID " + id + " não encontrado"));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Carro não encontrado");
        }
        repository.deleteById(id);
    }

    @Transactional
    public CarroResponseDTO vincularLoja(Long carroId, Long lojaId) {
        Carros carro = repository.findById(carroId)
                .orElseThrow(() -> new ResourceNotFoundException("Carro não encontrado"));
        Lojas loja = lojasRepository.findById(lojaId)
                .orElseThrow(() -> new ResourceNotFoundException("Loja não encontrada"));

        carro.setLojas(loja);
        carro.setDataAtualizacao(LocalDateTime.now());
        return mapToResponseDTO(repository.save(carro));
    }

    @Transactional
    public CarroResponseDTO transferirVeiculo(Long carroId, TransferenciaCarroDTO dto) {
        Carros carro = repository.findById(carroId)
                .orElseThrow(() -> new ResourceNotFoundException("Carro não encontrado"));

        if (carro.getStatus() == StatusVeiculo.VENDIDO) {
            throw new BusinessException("Não é possível transferir um veículo já vendido");
        }

        Lojas lojaDestino = lojasRepository.findById(dto.getLojaDestinoId())
                .orElseThrow(() -> new ResourceNotFoundException("Loja destino não encontrada"));

        carro.setLojas(lojaDestino);
        carro.setDataAtualizacao(LocalDateTime.now());
        return mapToResponseDTO(repository.save(carro));
    }

    @Transactional
    public CarroResponseDTO atualizarStatus(Long carroId, AtualizacaoStatusDTO dto) {
        Carros carro = repository.findById(carroId)
                .orElseThrow(() -> new ResourceNotFoundException("Carro não encontrado"));

        validarTransicaoStatus(carro.getStatus(), dto.getNovoStatus());

        carro.setStatus(dto.getNovoStatus());
        carro.setDataAtualizacao(LocalDateTime.now());
        return mapToResponseDTO(repository.save(carro));
    }

    public Page<CarroResponseDTO> listarPorStatus(StatusVeiculo status, Pageable pageable) {
        return repository.findByStatus(status, pageable).map(this::mapToResponseDTO);
    }

    public Page<CarroResponseDTO> listarPorLoja(Long lojaId, Pageable pageable) {
        return repository.findByLojasId(lojaId, pageable).map(this::mapToResponseDTO);
    }

    public List<CarroResponseDTO> listarPorLojaEStatus(Long lojaId, StatusVeiculo status) {
        return repository.findByLojasIdAndStatus(lojaId, status).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private void validarTransicaoStatus(StatusVeiculo atual, StatusVeiculo novo) {
        if (atual == StatusVeiculo.VENDIDO) {
            throw new BusinessException("Veículo vendido não pode ter seu status alterado");
        }
        if (atual == StatusVeiculo.RESERVADO && novo == StatusVeiculo.EM_MANUTENCAO) {
            throw new BusinessException("Veículo reservado não pode ir direto para manutenção. Cancele a reserva primeiro.");
        }
    }

    private void mapRequestToEntity(CarroRequestDTO dto, Carros carro) {
        carro.setNome(dto.getNome());
        carro.setMarca(dto.getMarca());
        carro.setCor(dto.getCor());
        carro.setAno(dto.getAno());
        carro.setPreco(dto.getPreco());
        carro.setQuilometragem(dto.getQuilometragem());
        carro.setCombustivel(dto.getCombustivel());
        carro.setCambio(dto.getCambio());
        carro.setDescricao(dto.getDescricao());
    }

    private CarroResponseDTO mapToResponseDTO(Carros carro) {
        CarroResponseDTO dto = new CarroResponseDTO();
        dto.setId(carro.getId());
        dto.setNome(carro.getNome());
        dto.setMarca(carro.getMarca());
        dto.setCor(carro.getCor());
        dto.setAno(carro.getAno());
        dto.setPreco(carro.getPreco());
        dto.setQuilometragem(carro.getQuilometragem());
        dto.setCombustivel(carro.getCombustivel());
        dto.setCambio(carro.getCambio());
        dto.setDescricao(carro.getDescricao());
        dto.setStatus(carro.getStatus());
        dto.setDataCriacao(carro.getDataCriacao());
        dto.setDataAtualizacao(carro.getDataAtualizacao());
        if (carro.getLojas() != null) {
            dto.setLojaId(carro.getLojas().getId());
            dto.setLojaNome(carro.getLojas().getNome());
        }
        return dto;
    }
}

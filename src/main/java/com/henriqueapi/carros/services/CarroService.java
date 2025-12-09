package com.henriqueapi.carros.services;

import com.henriqueapi.carros.dtos.AtualizacaoStatusDTO;
import com.henriqueapi.carros.dtos.Request.CarroRequestDTO;
import com.henriqueapi.carros.dtos.Response.CarroResponseDTO;
import com.henriqueapi.carros.dtos.TransferenciaCarroDTO;
import com.henriqueapi.carros.entity.Carros;
import com.henriqueapi.carros.entity.Lojas;
import com.henriqueapi.carros.entity.enums.StatusVeiculo;
import com.henriqueapi.carros.repository.CarroRepository;
import com.henriqueapi.carros.repository.LojaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarroService {

    @Autowired
    private CarroRepository repository;

    @Autowired
    private LojaRepository lojasRepository;

    public CarroResponseDTO create(CarroRequestDTO dto) {

        Carros carro = new Carros();
        carro.setNome(dto.getNome());
        carro.setCor(dto.getCor());
        carro.setMarca(dto.getMarca());
        carro.setAno(dto.getAno());
        carro.setStatus(dto.getStatus() != null ? dto.getStatus() : StatusVeiculo.DISPONIVEL);

        if (dto.getLojaId() != null) {
            Lojas loja = lojasRepository.findById(dto.getLojaId())
                    .orElseThrow(() -> new EntityNotFoundException("Loja não encontrada"));
            carro.setLojas(loja);
        }

        Carros saved = repository.save(carro);
        return mapToResponseDTO(saved);
    }

    public CarroResponseDTO update(Long id, CarroRequestDTO dto) {
        Carros carro = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Carro com ID " + id + " não encontrado"));

        carro.setNome(dto.getNome());
        carro.setCor(dto.getCor());
        carro.setMarca(dto.getMarca());
        carro.setAno(dto.getAno());
        if (dto.getStatus() != null) {
            carro.setStatus(dto.getStatus());
        }
        if (dto.getLojaId() != null) {
            Lojas loja = lojasRepository.findById(dto.getLojaId())
                    .orElseThrow(() -> new EntityNotFoundException("Loja não encontrada"));
            carro.setLojas(loja);
        }

        Carros atualizado = repository.save(carro);
        return mapToResponseDTO(atualizado);
    }

    public Page<CarroResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .map(this::mapToResponseDTO);
    }

    public CarroResponseDTO findById(Long id){
        Carros carro = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Carro com ID " + id + " não encontrado"));
        return mapToResponseDTO(carro);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Carro não encontrado");
        }
        repository.deleteById(id);
    }

    @Transactional
    public CarroResponseDTO vincularLoja(Long carroId, Long lojaId) {
        Carros carro = repository.findById(carroId)
                .orElseThrow(() -> new EntityNotFoundException("Carro não encontrado"));

        Lojas loja = lojasRepository.findById(lojaId)
                .orElseThrow(() -> new EntityNotFoundException("Loja não encontrada"));

        carro.setLojas(loja);
        Carros atualizado = repository.save(carro);
        return mapToResponseDTO(atualizado);
    }

    @Transactional
    public CarroResponseDTO transferirVeiculo(Long carroId, TransferenciaCarroDTO dto) {
        Carros carro = repository.findById(carroId)
                .orElseThrow(() -> new EntityNotFoundException("Carro não encontrado"));

        Lojas lojaDestino = lojasRepository.findById(dto.getLojaDestinoId())
                .orElseThrow(() -> new EntityNotFoundException("Loja destino não encontrada"));

        carro.setLojas(lojaDestino);
        Carros transferido = repository.save(carro);

        return mapToResponseDTO(transferido);
    }


    @Transactional
    public CarroResponseDTO atualizarStatus(Long carroId, AtualizacaoStatusDTO dto){
        Carros carro = repository.findById(carroId)
                .orElseThrow(() -> new EntityNotFoundException("Carro não encontrado"));

        carro.setStatus(dto.getNovoStatus());
        Carros atualizado = repository.save(carro);

        return mapToResponseDTO(atualizado);
    }


    public List<CarroResponseDTO> listarPorStatus(StatusVeiculo status) {
        return repository.findByStatus(status).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<CarroResponseDTO> listarPorLoja(Long lojaId) {
        return repository.findByLojasId(lojaId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<CarroResponseDTO> listarPorLojaEStatus(Long lojaId, StatusVeiculo status) {
        return repository.findByLojasIdAndStatus(lojaId, status).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private CarroResponseDTO mapToResponseDTO(Carros carro){
        CarroResponseDTO dto = new CarroResponseDTO();
        dto.setId(carro.getId());
        dto.setNome(carro.getNome());
        dto.setMarca(carro.getMarca());
        dto.setCor(carro.getCor());
        dto.setAno(carro.getAno());
        dto.setStatus(carro.getStatus());
        if (carro.getLojas() != null) {
            dto.setLojaId(carro.getLojas().getId());
            dto.setLojaNome(carro.getLojas().getNome());
        }
        return dto;
    }
}
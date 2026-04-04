package com.henriqueapi.carros.services;

import com.henriqueapi.carros.dtos.Request.LojaRequestDTO;
import com.henriqueapi.carros.dtos.Response.CarroResponseDTO;
import com.henriqueapi.carros.dtos.Response.LojaResponseDTO;
import com.henriqueapi.carros.entity.Carros;
import com.henriqueapi.carros.entity.Lojas;
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
public class LojaService {

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private CarroRepository carroRepository;

    @Transactional
    public LojaResponseDTO create(LojaRequestDTO dto) {
        if (dto.getCnpj() != null && lojaRepository.findByCnpj(dto.getCnpj()).isPresent()) {
            throw new BusinessException("CNPJ já cadastrado");
        }

        Lojas loja = new Lojas();
        mapRequestToEntity(dto, loja);
        return mapToResponseDTO(lojaRepository.save(loja));
    }

    @Transactional
    public LojaResponseDTO update(Long id, LojaRequestDTO dto) {
        Lojas loja = lojaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loja com ID " + id + " não encontrada"));

        if (dto.getCnpj() != null && !dto.getCnpj().equals(loja.getCnpj())) {
            if (lojaRepository.findByCnpj(dto.getCnpj()).isPresent()) {
                throw new BusinessException("CNPJ já cadastrado");
            }
        }

        mapRequestToEntity(dto, loja);
        loja.setDataAtualizacao(LocalDateTime.now());
        return mapToResponseDTO(lojaRepository.save(loja));
    }

    public Page<LojaResponseDTO> findAll(Pageable pageable) {
        return lojaRepository.findAll(pageable).map(this::mapToResponseDTO);
    }

    public LojaResponseDTO findById(Long id) {
        Lojas loja = lojaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loja com ID " + id + " não encontrada"));
        return mapToResponseDTO(loja);
    }

    @Transactional
    public void delete(Long id) {
        if (!lojaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Loja não encontrada");
        }
        lojaRepository.deleteById(id);
    }

    public List<CarroResponseDTO> listarCarrosDaLoja(Long lojaId) {
        if (!lojaRepository.existsById(lojaId)) {
            throw new ResourceNotFoundException("Loja não encontrada");
        }
        return carroRepository.findByLojasId(lojaId).stream()
                .map(this::mapCarroToResponseDTO)
                .collect(Collectors.toList());
    }

    private void mapRequestToEntity(LojaRequestDTO dto, Lojas loja) {
        loja.setNome(dto.getNome());
        loja.setCnpj(dto.getCnpj());
        loja.setEndereco(dto.getEndereco());
        loja.setCidade(dto.getCidade());
        loja.setEstado(dto.getEstado());
        loja.setTelefone(dto.getTelefone());
    }

    private LojaResponseDTO mapToResponseDTO(Lojas loja) {
        LojaResponseDTO dto = new LojaResponseDTO();
        dto.setId(loja.getId());
        dto.setNome(loja.getNome());
        dto.setCnpj(loja.getCnpj());
        dto.setEndereco(loja.getEndereco());
        dto.setCidade(loja.getCidade());
        dto.setEstado(loja.getEstado());
        dto.setTelefone(loja.getTelefone());
        dto.setAtivo(loja.getAtivo());
        dto.setDataCriacao(loja.getDataCriacao());

        dto.setQuantidadeVeiculos((int) carroRepository.countByLojaId(loja.getId()));
        return dto;
    }

    private CarroResponseDTO mapCarroToResponseDTO(Carros carro) {
        CarroResponseDTO dto = new CarroResponseDTO();
        dto.setId(carro.getId());
        dto.setNome(carro.getNome());
        dto.setMarca(carro.getMarca());
        dto.setCor(carro.getCor());
        dto.setAno(carro.getAno());
        dto.setPreco(carro.getPreco());
        dto.setQuilometragem(carro.getQuilometragem());
        dto.setStatus(carro.getStatus());
        if (carro.getLojas() != null) {
            dto.setLojaId(carro.getLojas().getId());
            dto.setLojaNome(carro.getLojas().getNome());
        }
        return dto;
    }
}

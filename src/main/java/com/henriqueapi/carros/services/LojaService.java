package com.henriqueapi.carros.services;

import com.henriqueapi.carros.dtos.Request.LojaRequestDTO;
import com.henriqueapi.carros.dtos.Response.CarroResponseDTO;
import com.henriqueapi.carros.dtos.Response.LojaResponseDTO;
import com.henriqueapi.carros.entity.Carros;
import com.henriqueapi.carros.entity.Lojas;
import com.henriqueapi.carros.repository.CarroRepository;
import com.henriqueapi.carros.repository.LojaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class LojaService {

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private CarroRepository carroRepository;

    public LojaResponseDTO create(LojaRequestDTO dto) {
        Lojas loja = new Lojas();
        loja.setNome(dto.getNome());
        loja.setCnpj(dto.getCnpj());
        loja.setEndereco(dto.getEndereco());
        loja.setCidade(dto.getCidade());
        loja.setEstado(dto.getEstado());
        loja.setTelefone(dto.getTelefone());

        Lojas saved = lojaRepository.save(loja);
        return mapToResponseDTO(saved);
    }

    public LojaResponseDTO update(Long id, LojaRequestDTO dto) {
        Lojas loja = lojaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Loja com ID " + id + " não encontrada"));

        loja.setNome(dto.getNome());
        loja.setCnpj(dto.getCnpj());
        loja.setEndereco(dto.getEndereco());
        loja.setCidade(dto.getCidade());
        loja.setEstado(dto.getEstado());
        loja.setTelefone(dto.getTelefone());

        Lojas atualizada = lojaRepository.save(loja);
        return mapToResponseDTO(atualizada);
    }

    public Page<LojaResponseDTO> findAll(Pageable pageable) {
        return lojaRepository.findAll(pageable)
                .map(this::mapToResponseDTO);
    }

    public LojaResponseDTO findById(Long id) {
        Lojas loja = lojaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Loja com ID " + id + " não encontrada"));
        return mapToResponseDTO(loja);
    }

    public void delete(Long id) {
        if (!lojaRepository.existsById(id)) {
            throw new EntityNotFoundException("Loja não encontrada");
        }
        lojaRepository.deleteById(id);
    }

    public List<CarroResponseDTO> listarCarrosDaLoja(Long lojaId) {
        if (!lojaRepository.existsById(lojaId)) {
            throw new EntityNotFoundException("Loja não encontrada");
        }

        return carroRepository.findByLojasId(lojaId).stream()
                .map(this::mapCarroToResponseDTO)
                .collect(Collectors.toList());
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

        // Conta quantidade de veículos da loja
        if (loja.getCarros() != null) {
            dto.setQuantidadeVeiculos(loja.getCarros().size());
        } else {
            dto.setQuantidadeVeiculos(0);
        }

        return dto;
    }

    private CarroResponseDTO mapCarroToResponseDTO(Carros carro) {
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
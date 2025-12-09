package com.henriqueapi.carros.services;

import com.henriqueapi.carros.dtos.CarroRequestDTO;
import com.henriqueapi.carros.dtos.CarroResponseDTO;
import com.henriqueapi.carros.entity.Carros;
import com.henriqueapi.carros.repository.CarroRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CarroService {

    @Autowired

    private CarroRepository repository;

    public CarroResponseDTO create(CarroRequestDTO dto) {

        Carros carro = new Carros();
        carro.setNome(dto.getNome());
        carro.setCor(dto.getCor());
        carro.setMarca(dto.getMarca());
        carro.setAno(dto.getAno());

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

        Carros atualizado = repository.save(carro);
        return mapToResponseDTO(atualizado);
    }

    public Page<CarroResponseDTO> findAll(Pageable pageable) {
        return  repository.findAll(pageable)
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

    private CarroResponseDTO mapToResponseDTO(Carros carro){
        CarroResponseDTO dto = new CarroResponseDTO();
        dto.setId(carro.getId());
        dto.setNome(carro.getNome());
        dto.setMarca(carro.getMarca());
        dto.setCor(carro.getCor());
        dto.setAno(carro.getAno());
        return dto;
    }
}
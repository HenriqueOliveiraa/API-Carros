package com.henriqueapi.carros.controller;

import com.henriqueapi.carros.dtos.AtualizacaoStatusDTO;
import com.henriqueapi.carros.dtos.Request.CarroRequestDTO;
import com.henriqueapi.carros.dtos.Response.CarroResponseDTO;
import com.henriqueapi.carros.dtos.TransferenciaCarroDTO;
import com.henriqueapi.carros.entity.enums.StatusVeiculo;
import com.henriqueapi.carros.services.CarroService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.henriqueapi.carros.repository.CarroRepository;

import java.util.List;


@RestController
@RequestMapping(value = "/api/carros")
public class CarroController {

    @Autowired
    private CarroRepository repository;

    @Autowired
    private CarroService service;

    @GetMapping
    public ResponseEntity<Page<CarroResponseDTO>> findAll(Pageable pageable) {
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarroResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<CarroResponseDTO> create(@RequestBody CarroRequestDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarroResponseDTO> update (@PathVariable Long id, @RequestBody CarroRequestDTO dto){
        try{
            CarroResponseDTO response = service.update(id, dto);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}/vincular-loja/{lojaId}")
    public ResponseEntity<CarroResponseDTO> vincularLoja(
            @PathVariable Long id,
            @PathVariable Long lojaId) {
        CarroResponseDTO vinculado = service.vincularLoja(id, lojaId);
        return ResponseEntity.ok(vinculado);
    }

    @PutMapping("/{id}/transferir")
    public ResponseEntity<CarroResponseDTO> transferir(
            @PathVariable Long id,
            @RequestBody TransferenciaCarroDTO dto) {
        CarroResponseDTO transferido = service.transferirVeiculo(id, dto);
        return ResponseEntity.ok(transferido);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CarroResponseDTO> atualizarStatus(
            @PathVariable Long id,
            @RequestBody AtualizacaoStatusDTO dto) {
        CarroResponseDTO atualizado = service.atualizarStatus(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<CarroResponseDTO>> listarPorStatus(@PathVariable StatusVeiculo status) {
        List<CarroResponseDTO> carros = service.listarPorStatus(status);
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/loja/{lojaId}")
    public ResponseEntity<List<CarroResponseDTO>> listarPorLoja(@PathVariable Long lojaId) {
        List<CarroResponseDTO> carros = service.listarPorLoja(lojaId);
        return ResponseEntity.ok(carros);
    }

    @GetMapping("/loja/{lojaId}/status/{status}")
    public ResponseEntity<List<CarroResponseDTO>> listarPorLojaEStatus(
            @PathVariable Long lojaId,
            @PathVariable StatusVeiculo status) {
        List<CarroResponseDTO> carros = service.listarPorLojaEStatus(lojaId, status);
        return ResponseEntity.ok(carros);
    }
}
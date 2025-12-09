package com.henriqueapi.carros.controller;


import com.henriqueapi.carros.dtos.Request.LojaRequestDTO;
import com.henriqueapi.carros.dtos.Response.CarroResponseDTO;
import com.henriqueapi.carros.dtos.Response.LojaResponseDTO;
import com.henriqueapi.carros.services.LojaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/lojas")
public class LojasController {

    @Autowired
    private LojaService service;


    @PostMapping
    public ResponseEntity<LojaResponseDTO> criar(@RequestBody LojaRequestDTO dto) {
        LojaResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<Page<LojaResponseDTO>> listarTodas(Pageable pageable) {
        Page<LojaResponseDTO> lojas = service.findAll(pageable);
        return ResponseEntity.ok(lojas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LojaResponseDTO> buscarPorId(@PathVariable Long id) {
        LojaResponseDTO loja = service.findById(id);
        return ResponseEntity.ok(loja);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LojaResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody LojaRequestDTO dto) {
        LojaResponseDTO updated = service.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/carros")
    public ResponseEntity<List<CarroResponseDTO>> listarCarrosDaLoja(@PathVariable Long id) {
        List<CarroResponseDTO> carros = service.listarCarrosDaLoja(id);
        return ResponseEntity.ok(carros);
    }
}

package com.henriqueapi.carros.services;

import com.henriqueapi.carros.dtos.CarroResponseDTO;
import com.henriqueapi.carros.dtos.LojaDTO;
import com.henriqueapi.carros.repository.CarroRepository;
import com.henriqueapi.carros.repository.LojaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LojaService {

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private CarroRepository carroRepository;

    public LojaDTO criarLoja(LojaDTO dto) {
        // validações e criação
        return dto;
    }

}

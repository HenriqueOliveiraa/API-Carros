package com.henriqueapi.carros.services;

import com.henriqueapi.carros.dtos.Request.AlterarSenhaDTO;
import com.henriqueapi.carros.dtos.Request.UsuarioRequestDTO;
import com.henriqueapi.carros.dtos.Response.UsuarioResponseDTO;
import com.henriqueapi.carros.entity.Lojas;
import com.henriqueapi.carros.entity.Usuarios;
import com.henriqueapi.carros.entity.enums.TipoUsuario;
import com.henriqueapi.carros.repository.LojaRepository;
import com.henriqueapi.carros.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Transactional
    public UsuarioResponseDTO create(UsuarioRequestDTO dto){

        if(repository.existsByEmail(dto.getEmail())){
            throw new IllegalArgumentException("Email já cadastrado");
        }

        if (repository.existsByCpf(dto.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado");
        }

        if ((dto.getTipo() == TipoUsuario.VENDEDOR || dto.getTipo() == TipoUsuario.GERENTE)
                && dto.getLojaId() == null) {
            throw new IllegalArgumentException("Vendedores e Gerentes precisam estar vinculados a uma loja");
        }

        Usuarios usuario = new Usuarios();

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setCpf(dto.getCpf());
        usuario.setTelefone(dto.getTelefone());
        usuario.setTipo(dto.getTipo());
        usuario.setAtivo(true);
        usuario.setDataCriacao(LocalDateTime.now());

        if (dto.getLojaId() != null) {
            Lojas loja = lojaRepository.findById(dto.getLojaId())
                    .orElseThrow(() -> new EntityNotFoundException("Loja não encontrada"));
            usuario.setLoja(loja);
        }

        Usuarios saved = repository.save(usuario);
        return mapToResponseDTO(saved);

    }


    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO dto) {
        Usuarios usuario = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));


        if (!usuario.getEmail().equals(dto.getEmail()) && repository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado");
        }

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setDataAtualizacao(LocalDateTime.now());

        if (dto.getLojaId() != null) {
            Lojas loja = lojaRepository.findById(dto.getLojaId())
                    .orElseThrow(() -> new EntityNotFoundException("Loja não encontrada"));
            usuario.setLoja(loja);
        }

        Usuarios updated = repository.save(usuario);
        return mapToResponseDTO(updated);
    }


    public UsuarioResponseDTO findById(Long id){
        Usuarios usuario = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado") );
        return mapToResponseDTO(usuario);
    }

    public List<UsuarioResponseDTO> findAll() {
        return repository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<UsuarioResponseDTO> findByTipo(TipoUsuario tipo) {
        return repository.findByTipo(tipo).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public List<UsuarioResponseDTO> findByLoja(Long lojaId) {
        return repository.findByLojaId(lojaId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void ativarDesativar(Long id, Boolean ativo) {
        Usuarios usuario = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        usuario.setAtivo(ativo);
        usuario.setDataAtualizacao(LocalDateTime.now());
        repository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long id, AlterarSenhaDTO dto){
        Usuarios usuario = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.getSenhaAtual(), usuario.getSenha())){
            throw new IllegalArgumentException("Senha atual incorreta");
        }

        usuario.setSenha(passwordEncoder.encode(dto.getNovaSenha()));
        usuario.setDataAtualizacao(LocalDateTime.now());
        repository.save(usuario);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
        repository.deleteById(id);
    }


    private UsuarioResponseDTO mapToResponseDTO(Usuarios usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNome(usuario.getNome());
        dto.setEmail(usuario.getEmail());
        dto.setCpf(usuario.getCpf());
        dto.setTelefone(usuario.getTelefone());
        dto.setTipo(usuario.getTipo());
        dto.setAtivo(usuario.getAtivo());
        dto.setDataCriacao(usuario.getDataCriacao());

        if (usuario.getLoja() != null) {
            dto.setLojaId(usuario.getLoja().getId());
            dto.setLojaNome(usuario.getLoja().getNome());
        }

        return dto;
    }
}

package com.henriqueapi.carros.services;

import com.henriqueapi.carros.dtos.Request.AlterarSenhaDTO;
import com.henriqueapi.carros.dtos.Request.RegistroRequestDTO;
import com.henriqueapi.carros.dtos.Request.UsuarioRequestDTO;
import com.henriqueapi.carros.dtos.Response.UsuarioResponseDTO;
import com.henriqueapi.carros.entity.Lojas;
import com.henriqueapi.carros.entity.Usuarios;
import com.henriqueapi.carros.entity.enums.TipoUsuario;
import com.henriqueapi.carros.exception.BusinessException;
import com.henriqueapi.carros.exception.ResourceNotFoundException;
import com.henriqueapi.carros.repository.LojaRepository;
import com.henriqueapi.carros.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDTO registrar(RegistroRequestDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }
        if (dto.getCpf() != null && repository.existsByCpf(dto.getCpf())) {
            throw new BusinessException("CPF já cadastrado");
        }

        Usuarios usuario = new Usuarios();
        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setSenha(passwordEncoder.encode(dto.getSenha()));
        usuario.setCpf(dto.getCpf());
        usuario.setTelefone(dto.getTelefone());
        usuario.setTipo(TipoUsuario.CLIENTE);
        usuario.setAtivo(true);
        usuario.setDataCriacao(LocalDateTime.now());

        return mapToResponseDTO(repository.save(usuario));
    }

    @Transactional
    public UsuarioResponseDTO create(UsuarioRequestDTO dto) {
        if (repository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }
        if (dto.getCpf() != null && repository.existsByCpf(dto.getCpf())) {
            throw new BusinessException("CPF já cadastrado");
        }
        if ((dto.getTipo() == TipoUsuario.VENDEDOR || dto.getTipo() == TipoUsuario.GERENTE)
                && dto.getLojaId() == null) {
            throw new BusinessException("Vendedores e Gerentes precisam estar vinculados a uma loja");
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
                    .orElseThrow(() -> new ResourceNotFoundException("Loja não encontrada"));
            usuario.setLoja(loja);
        }

        return mapToResponseDTO(repository.save(usuario));
    }

    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO dto) {
        Usuarios usuario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!usuario.getEmail().equals(dto.getEmail()) && repository.existsByEmail(dto.getEmail())) {
            throw new BusinessException("Email já cadastrado");
        }

        usuario.setNome(dto.getNome());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefone(dto.getTelefone());
        usuario.setDataAtualizacao(LocalDateTime.now());

        if (dto.getLojaId() != null) {
            Lojas loja = lojaRepository.findById(dto.getLojaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Loja não encontrada"));
            usuario.setLoja(loja);
        }

        return mapToResponseDTO(repository.save(usuario));
    }

    public UsuarioResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(this::mapToResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    public UsuarioResponseDTO findByEmail(String email) {
        return repository.findByEmail(email)
                .map(this::mapToResponseDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    public Page<UsuarioResponseDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::mapToResponseDTO);
    }

    public Page<UsuarioResponseDTO> findByTipo(TipoUsuario tipo, Pageable pageable) {
        return repository.findByTipo(tipo, pageable).map(this::mapToResponseDTO);
    }

    public Page<UsuarioResponseDTO> findByLoja(Long lojaId, Pageable pageable) {
        return repository.findByLojaId(lojaId, pageable).map(this::mapToResponseDTO);
    }

    @Transactional
    public void ativarDesativar(Long id, Boolean ativo) {
        Usuarios usuario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
        usuario.setAtivo(ativo);
        usuario.setDataAtualizacao(LocalDateTime.now());
        repository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long id, AlterarSenhaDTO dto) {
        Usuarios usuario = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!passwordEncoder.matches(dto.getSenhaAtual(), usuario.getSenha())) {
            throw new BusinessException("Senha atual incorreta");
        }

        usuario.setSenha(passwordEncoder.encode(dto.getNovaSenha()));
        usuario.setDataAtualizacao(LocalDateTime.now());
        repository.save(usuario);
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado");
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

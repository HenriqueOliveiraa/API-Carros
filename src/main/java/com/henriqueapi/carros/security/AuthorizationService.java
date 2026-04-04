package com.henriqueapi.carros.security;

import com.henriqueapi.carros.entity.Usuarios;
import com.henriqueapi.carros.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service("authorizationService")
public class AuthorizationService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean isOwner(Long userId, Authentication authentication) {
        String email = authentication.getName();
        return usuarioRepository.findById(userId)
                .map(u -> u.getEmail().equals(email))
                .orElse(false);
    }

    public boolean isFromLoja(Long lojaId, Authentication authentication) {
        String email = authentication.getName();
        return usuarioRepository.findByEmail(email)
                .map(u -> u.getLoja() != null && u.getLoja().getId().equals(lojaId))
                .orElse(false);
    }
}

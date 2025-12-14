package com.henriqueapi.carros.services;


import com.henriqueapi.carros.dtos.Request.LoginRequestDTO;
import com.henriqueapi.carros.dtos.Response.LoginResponseDTO;
import com.henriqueapi.carros.entity.Usuarios;
import com.henriqueapi.carros.repository.UsuarioRepository;
import com.henriqueapi.carros.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public LoginResponseDTO login(LoginRequestDTO dto){
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getSenha()));

            Usuarios usuario = usuarioRepository.findByEmail(dto.getEmail())
                    .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

            if (!usuario.getAtivo()){
                throw new RuntimeException("Usuário inativo");
            }

            String token = jwtService.generateToken(usuario);

            LoginResponseDTO response = new LoginResponseDTO();
            response.setToken(token);
            response.setId(usuario.getId());
            response.setNome(usuario.getNome());
            response.setEmail(usuario.getEmail());
            response.setTipoUsuario(usuario.getTipo());

            if (usuario.getLoja() != null){
                response.setLojaId(usuario.getLoja().getId());
                response.setLojaNome(usuario.getLoja().getNome());
            }

            return response;
        } catch (AuthenticationException e){
            throw new RuntimeException("Email ou senha inválidos");
        }
    }
}

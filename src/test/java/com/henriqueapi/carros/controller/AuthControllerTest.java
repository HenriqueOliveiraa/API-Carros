package com.henriqueapi.carros.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.henriqueapi.carros.dtos.Request.LoginRequestDTO;
import com.henriqueapi.carros.dtos.Request.RegistroRequestDTO;
import com.henriqueapi.carros.dtos.Response.LoginResponseDTO;
import com.henriqueapi.carros.dtos.Response.UsuarioResponseDTO;
import com.henriqueapi.carros.entity.enums.TipoUsuario;
import com.henriqueapi.carros.security.JwtService;
import com.henriqueapi.carros.security.SecurityConfig;
import com.henriqueapi.carros.security.UserDetailsServiceImpl;
import com.henriqueapi.carros.services.AuthService;
import com.henriqueapi.carros.services.UsuarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void login_comCredenciaisValidas_retorna200() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("henrique@admin.com");
        request.setSenha("Admin@123");

        LoginResponseDTO response = new LoginResponseDTO();
        response.setToken("jwt-token-valido");
        response.setEmail("henrique@admin.com");
        response.setTipoUsuario(TipoUsuario.ADMIN);

        when(authService.login(any())).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt-token-valido"))
                .andExpect(jsonPath("$.email").value("henrique@admin.com"))
                .andExpect(jsonPath("$.tipoUsuario").value("ADMIN"));
    }

    @Test
    void login_comEmailInvalido_retorna400() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("email-invalido");
        request.setSenha("Admin@123");

        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_semEmail_retorna400() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setSenha("Admin@123");

        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_semSenha_retorna400() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("henrique@admin.com");

        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_comCredenciaisInvalidas_retorna401() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("henrique@admin.com");
        request.setSenha("SenhaErrada@1");

        when(authService.login(any())).thenThrow(new BadCredentialsException("Bad credentials"));

        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void login_usuarioInativo_retorna401() throws Exception {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("inativo@email.com");
        request.setSenha("Senha@123");

        when(authService.login(any())).thenThrow(new DisabledException("Usuário inativo"));

        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.mensagem").value("Usuário inativo"));
    }

    @Test
    void registro_comDadosValidos_retorna201() throws Exception {
        RegistroRequestDTO request = new RegistroRequestDTO();
        request.setNome("João Silva");
        request.setEmail("joao@email.com");
        request.setSenha("Senha@123");

        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setId(1L);
        response.setNome("João Silva");
        response.setEmail("joao@email.com");
        response.setTipo(TipoUsuario.CLIENTE);

        when(usuarioService.registrar(any())).thenReturn(response);

        mockMvc.perform(post("/api/auth/registro")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.tipo").value("CLIENTE"));
    }

    @Test
    void registro_semNome_retorna400() throws Exception {
        RegistroRequestDTO request = new RegistroRequestDTO();
        request.setEmail("joao@email.com");
        request.setSenha("Senha@123");

        mockMvc.perform(post("/api/auth/registro")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registro_comEmailInvalido_retorna400() throws Exception {
        RegistroRequestDTO request = new RegistroRequestDTO();
        request.setNome("João Silva");
        request.setEmail("email-invalido");
        request.setSenha("Senha@123");

        mockMvc.perform(post("/api/auth/registro")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_semCorpoJson_retorna400() throws Exception {
        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }
}

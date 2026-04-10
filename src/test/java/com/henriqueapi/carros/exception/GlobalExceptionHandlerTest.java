package com.henriqueapi.carros.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void handleNotFound_resourceNotFoundException_retorna404() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Carro não encontrado");

        ResponseEntity<Map<String, Object>> response = handler.handleNotFound(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getBody().get("status"));
        assertEquals("Carro não encontrado", response.getBody().get("mensagem"));
    }

    @Test
    void handleBusiness_retorna400() {
        BusinessException ex = new BusinessException("Email já cadastrado");

        ResponseEntity<Map<String, Object>> response = handler.handleBusiness(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().get("status"));
        assertEquals("Email já cadastrado", response.getBody().get("mensagem"));
    }

    @Test
    void handleIllegalArgument_retorna400() {
        IllegalArgumentException ex = new IllegalArgumentException("Argumento inválido");

        ResponseEntity<Map<String, Object>> response = handler.handleIllegalArgument(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Argumento inválido", response.getBody().get("mensagem"));
    }

    @Test
    void handleAuthErrors_badCredentials_retorna401() {
        BadCredentialsException ex = new BadCredentialsException("Bad credentials");

        ResponseEntity<Map<String, Object>> response = handler.handleAuthErrors(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Email ou senha inválidos", response.getBody().get("mensagem"));
    }

    @Test
    void handleAuthErrors_disabledException_retornaMensagemInativo() {
        DisabledException ex = new DisabledException("User is disabled");

        ResponseEntity<Map<String, Object>> response = handler.handleAuthErrors(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Usuário inativo", response.getBody().get("mensagem"));
    }

    @Test
    void handleAuthErrors_lockedException_retornaMensagemBloqueado() {
        LockedException ex = new LockedException("Account is locked");

        ResponseEntity<Map<String, Object>> response = handler.handleAuthErrors(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Conta bloqueada", response.getBody().get("mensagem"));
    }

    @Test
    void handleAccessDenied_retorna403() {
        AccessDeniedException ex = new AccessDeniedException("Access denied");

        ResponseEntity<Map<String, Object>> response = handler.handleAccessDenied(ex);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
        assertEquals("Acesso negado", response.getBody().get("mensagem"));
    }

    @Test
    void handleGeneral_retorna500() {
        Exception ex = new RuntimeException("Erro inesperado");

        ResponseEntity<Map<String, Object>> response = handler.handleGeneral(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(500, response.getBody().get("status"));
        assertEquals("Erro interno do servidor", response.getBody().get("mensagem"));
    }

    @Test
    void handleValidationErrors_retorna400ComCampos() throws Exception {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "target");
        bindingResult.addError(new FieldError("target", "email", "Email é obrigatório"));
        bindingResult.addError(new FieldError("target", "nome", "Nome é obrigatório"));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Map<String, Object>> response = handler.handleValidationErrors(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(400, response.getBody().get("status"));
        assertNotNull(response.getBody().get("detalhes"));

        @SuppressWarnings("unchecked")
        Map<String, String> detalhes = (Map<String, String>) response.getBody().get("detalhes");
        assertEquals("Email é obrigatório", detalhes.get("email"));
        assertEquals("Nome é obrigatório", detalhes.get("nome"));
    }

    @Test
    void handleNotFound_respostaTemTimestamp() {
        ResourceNotFoundException ex = new ResourceNotFoundException("Não encontrado");

        ResponseEntity<Map<String, Object>> response = handler.handleNotFound(ex);

        assertNotNull(response.getBody().get("timestamp"));
        assertNotNull(response.getBody().get("erro"));
    }
}

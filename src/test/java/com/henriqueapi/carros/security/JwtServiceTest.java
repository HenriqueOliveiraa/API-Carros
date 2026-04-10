package com.henriqueapi.carros.security;

import com.henriqueapi.carros.entity.Lojas;
import com.henriqueapi.carros.entity.Usuarios;
import com.henriqueapi.carros.entity.enums.TipoUsuario;
import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;

    private static final String SECRET = "minha-chave-secreta-super-segura-para-jwt-token-deve-ter-no-minimo-256-bits-123456789";
    private static final long EXPIRATION = 86400000L;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", SECRET);
        ReflectionTestUtils.setField(jwtService, "expiration", EXPIRATION);
    }

    @Test
    void generateToken_retornaTokenNaoNulo() {
        Usuarios usuario = criarUsuario();

        String token = jwtService.generateToken(usuario);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void extractEmail_retornaEmailCorreto() {
        Usuarios usuario = criarUsuario();
        String token = jwtService.generateToken(usuario);

        String email = jwtService.extractEmail(token);

        assertEquals("henrique@admin.com", email);
    }

    @Test
    void validateToken_tokenValido_retornaTrue() {
        Usuarios usuario = criarUsuario();
        String token = jwtService.generateToken(usuario);

        Boolean valid = jwtService.validateToken(token, "henrique@admin.com");

        assertTrue(valid);
    }

    @Test
    void validateToken_emailDiferente_retornaFalse() {
        Usuarios usuario = criarUsuario();
        String token = jwtService.generateToken(usuario);

        Boolean valid = jwtService.validateToken(token, "outro@email.com");

        assertFalse(valid);
    }

    @Test
    void isTokenExpired_tokenValido_retornaFalse() {
        Usuarios usuario = criarUsuario();
        String token = jwtService.generateToken(usuario);

        Boolean expired = jwtService.isTokenExpired(token);

        assertFalse(expired);
    }

    @Test
    void isTokenExpired_tokenExpirado_lancaExpiredJwtException() {
        ReflectionTestUtils.setField(jwtService, "expiration", -1000L);
        Usuarios usuario = criarUsuario();
        String token = jwtService.generateToken(usuario);

        assertThrows(ExpiredJwtException.class, () -> jwtService.isTokenExpired(token));
    }

    @Test
    void generateToken_comLoja_incluiLojaIdNasClaims() {
        Usuarios usuario = criarUsuario();
        Lojas loja = new Lojas();
        loja.setId(5L);
        loja.setNome("Loja Central");
        usuario.setLoja(loja);

        String token = jwtService.generateToken(usuario);
        Long lojaId = jwtService.extractClaim(token, claims -> claims.get("lojaId", Long.class));

        assertEquals(5L, lojaId);
    }

    @Test
    void generateToken_semLoja_naoIncluiLojaId() {
        Usuarios usuario = criarUsuario();

        String token = jwtService.generateToken(usuario);
        Object lojaId = jwtService.extractClaim(token, claims -> claims.get("lojaId"));

        assertNull(lojaId);
    }

    @Test
    void generateToken_incluiTipoNasClaims() {
        Usuarios usuario = criarUsuario();

        String token = jwtService.generateToken(usuario);
        String tipo = jwtService.extractClaim(token, claims -> claims.get("tipo", String.class));

        assertEquals("ADMIN", tipo);
    }

    @Test
    void extractExpiration_retornaDataFutura() {
        Usuarios usuario = criarUsuario();
        String token = jwtService.generateToken(usuario);

        java.util.Date expDate = jwtService.extractExpiration(token);

        assertTrue(expDate.after(new java.util.Date()));
    }

    private Usuarios criarUsuario() {
        Usuarios usuario = new Usuarios();
        usuario.setId(1L);
        usuario.setNome("Henrique");
        usuario.setEmail("henrique@admin.com");
        usuario.setSenha("hashSenha");
        usuario.setTipo(TipoUsuario.ADMIN);
        usuario.setAtivo(true);
        return usuario;
    }
}

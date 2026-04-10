package com.henriqueapi.carros.config;

import com.henriqueapi.carros.entity.Usuarios;
import com.henriqueapi.carros.entity.enums.TipoUsuario;
import com.henriqueapi.carros.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Profile("dev")
public class DataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.default.email}")
    private String adminEmail;

    @Value("${admin.default.senha}")
    private String adminSenha;

    @Override
    public void run(String... args) {
        if (usuarioRepository.findByEmail(adminEmail).isEmpty()) {
            Usuarios admin = new Usuarios();
            admin.setNome("Henrique");
            admin.setEmail(adminEmail);
            admin.setSenha(passwordEncoder.encode(adminSenha));
            admin.setCpf("000.000.000-00");
            admin.setTelefone("(00) 00000-0000");
            admin.setTipo(TipoUsuario.ADMIN);
            admin.setAtivo(true);
            admin.setDataCriacao(LocalDateTime.now());

            usuarioRepository.save(admin);
            log.info("Admin padrão criado: {}", adminEmail);
        }
    }
}

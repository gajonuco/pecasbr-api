package com.gajonuco.pecasbr.security;

import com.gajonuco.pecasbr.model.Role;
import com.gajonuco.pecasbr.model.Usuario;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JWTTokenUtilTest {

    private JWTTokenUtil jwtTokenUtil;

    @BeforeEach
    void setUp() {
        jwtTokenUtil = new JWTTokenUtil("chave-de-teste-com-mais-de-32-caracteres");
    }

    private Usuario criarUsuario(Role role) {
        Usuario usuario = new Usuario();
        usuario.setUsername("gabriel");
        usuario.setRole(role);
        return usuario;
    }

    @Test
    void tokenGeradoDeveCarregarARoleDoUsuario() {
        String token = jwtTokenUtil.generateToken(criarUsuario(Role.ADMIN));

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn(token);

        Authentication auth = jwtTokenUtil.decodeToken(request);

        assertNotNull(auth);
        assertTrue(auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void deveRetornarNullParaTokenAssinadoComOutraChave() {
        JWTTokenUtil outraInstancia = new JWTTokenUtil("outra-chave-completamente-diferente-123");
        String token = outraInstancia.generateToken(criarUsuario(Role.VENDEDOR));

        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn(token);

        assertNull(jwtTokenUtil.decodeToken(request));
    }

    @Test
    void deveRetornarNullQuandoNaoHouverHeaderAuthorization() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader("Authorization")).thenReturn(null);

        assertNull(jwtTokenUtil.decodeToken(request));
    }
}
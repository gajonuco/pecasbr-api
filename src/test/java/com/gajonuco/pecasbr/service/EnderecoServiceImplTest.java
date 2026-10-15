package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.dao.EnderecoDAO;
import com.gajonuco.pecasbr.dto.EnderecoDTO;
import com.gajonuco.pecasbr.exception.EnderecoNaoEncontradoException;
import com.gajonuco.pecasbr.model.Cliente;
import com.gajonuco.pecasbr.model.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EnderecoServiceImplTest {

    @Mock
    private EnderecoDAO dao;

    private EnderecoServiceImpl service;
    private Cliente cliente;

    @BeforeEach
    void setUp(){
        service = new EnderecoServiceImpl(dao);
        cliente = new Cliente();
        cliente.setId(1);
    }

    private EnderecoDTO dadosEndereco(){
        return new EnderecoDTO("Casa", "89800-000", "Rua A", "100", null, "Centro", "Chapecó", "SC");
    }

    @Test
    void primeiroEnderecoCriadoViraPrincipalAutomaticamente(){
        when(dao.findByClienteId(1)).thenReturn(List.of());
        when(dao.save(any(Endereco.class))).thenAnswer(inv -> inv.getArgument(0));

        Endereco resultado = service.criar(cliente, dadosEndereco());

        assertTrue(resultado.isPrincipal());
    }

    @Test
    void segundoEnderecoNaoViraPrincipalAutomaticamente(){
        Endereco existente = new Endereco();
        existente.setPrincipal(true);

        when(dao.findByClienteId(1)).thenReturn(List.of(existente));
        when(dao.save(any(Endereco.class))).thenAnswer(inv -> inv.getArgument(0));

        Endereco resultado = service.criar(cliente, dadosEndereco());

        assertFalse(resultado.isPrincipal());
    }

    @Test
    void marcarComoPrincipalDesmarcaOAnterior(){
        Endereco antigo = new Endereco();
        antigo.setId(1);
        antigo.setPrincipal(true);
        Endereco novo = new Endereco();
        novo.setId(2);

        when(dao.findByIdAndClienteId(2,1)).thenReturn(Optional.of(novo));
        when(dao.findByClienteId(1)).thenReturn(List.of(antigo,novo));
        when(dao.save(any(Endereco.class))).thenAnswer(inv -> inv.getArgument(0));

        Endereco resultado = service.marcarComoPrincipal(cliente,2);

        assertTrue(resultado.isPrincipal());
        assertFalse(antigo.isPrincipal());
    }

    @Test
    void removerOPrincipalPromoveOMaisAntigoRestante(){
        Endereco principal = new Endereco();
        principal.setId(1);
        principal.setPrincipal(true);
        Endereco outro = new Endereco();
        outro.setId(2);

        when(dao.findByIdAndClienteId(1,1)).thenReturn(Optional.of(principal));
        when(dao.findByClienteId(1)).thenReturn(List.of(outro)); // estado após o delete
        when(dao.save(any(Endereco.class))).thenAnswer(invocation -> invocation.getArgument(0));

        service.remover(cliente,1);

        verify(dao).delete(principal);
        assertTrue(outro.isPrincipal());

    }

    @Test
    void removerOUnicoEnderecoNaoQuebra(){
        Endereco unico = new Endereco();
        unico.setId(1);
        unico.setPrincipal(true);

        when(dao.findByIdAndClienteId(1,1)).thenReturn(Optional.of(unico));
        when(dao.findByClienteId(1)).thenReturn(List.of());

        assertDoesNotThrow(() -> service.remover(cliente,1));
        verify(dao).delete(unico);
    }

    @Test
    void naoPermiteAcessarEnderecoDeOutroCliente(){
        when(dao.findByIdAndClienteId(99,1)).thenReturn(Optional.empty());

        assertThrows(EnderecoNaoEncontradoException.class, () -> service.atualizar(cliente,99, dadosEndereco()));
    }
}

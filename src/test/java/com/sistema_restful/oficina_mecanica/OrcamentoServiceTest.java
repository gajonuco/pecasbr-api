package com.sistema_restful.oficina_mecanica;

import com.sistema_restful.oficina_mecanica.dto.OrcamentoDTO;
import com.sistema_restful.oficina_mecanica.model.*;
import com.sistema_restful.oficina_mecanica.repo.*;
import com.sistema_restful.oficina_mecanica.service.OrcamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrcamentoServiceTest {

    @Mock
    private OrcamentoRepository orcamentoRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ServicoRepository servicoRepository;
    @Mock
    private PecaRepository pecaRepository;

    @InjectMocks
    private OrcamentoService orcamentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void salvarOrcamento_comDescontoAplicado() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);

        Servico servico = new Servico();
        servico.setId(1L);
        servico.setPreco(100.0);

        Peca peca = new Peca();
        peca.setId(1L);
        peca.setPreco(50.0);

        OrcamentoDTO orcamentoDTO = new OrcamentoDTO();
        orcamentoDTO.setClienteId(1L);
        orcamentoDTO.setServicos(List.of(new OrcamentoDTO.ServicoQuantidadeDTO(1L, 1)));
        orcamentoDTO.setPecas(List.of(new OrcamentoDTO.PecaQuantidadeDTO(1L, 1)));

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(servicoRepository.findById(1L)).thenReturn(Optional.of(servico));
        when(pecaRepository.findById(1L)).thenReturn(Optional.of(peca));

        Orcamento orcamento = orcamentoService.salvarOrcamento(orcamentoDTO);

        // Verificar que o desconto foi aplicado
        double valorEsperado = (100.0 + 50.0) * 0.9; // 10% de desconto aplicado
        assertEquals(valorEsperado, orcamento.getValorTotal());
    }
}

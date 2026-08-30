package com.gajonuco.pecasbr.integration.service;

import com.gajonuco.pecasbr.integration.dto.DTOClienteListResponse;
import com.gajonuco.pecasbr.integration.dto.DTOClienteResponse;
import com.gajonuco.pecasbr.integration.dto.DTOResponse;
import com.gajonuco.pecasbr.model.Cliente;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AsaasServiceImplTest {

    @Mock
    private RestClient restClient;
    @Mock
    private RestClient.RequestBodyUriSpec postUriSpec;
    @Mock
    private RestClient.RequestBodySpec bodySpec;
    @Mock
    private RestClient.ResponseSpec postResponseSpec;
    @Mock
    private RestClient.RequestHeadersUriSpec getUriSpec;
    @Mock
    private RestClient.RequestHeadersSpec getHeadersSpec;
    @Mock
    private RestClient.ResponseSpec getResponseSpec;

    private AsaasServiceImpl asaasService;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        asaasService = new AsaasServiceImpl(mock(RestClient.Builder.class));

        ReflectionTestUtils.setField(
                asaasService,
                "restClient",
                restClient
        );

        ReflectionTestUtils.setField(
                asaasService,
                "baseURL",
                "https://api-sandbox.asaas.com/v3"
        );

        ReflectionTestUtils.setField(
                asaasService,
                "apiKey",
                "chave-fake-de-teste"
        );

        ReflectionTestUtils.setField(
                asaasService,
                "frontendUrl",
                "http://localhost:4222"
        );

        cliente = new Cliente();
        cliente.setNome("Maria Teste");
        cliente.setEmail("maria@teste.com");
        cliente.setTelefone("47999998888");
        cliente.setCpf("12345678900");

        // POST
        lenient().when(restClient.post())
                .thenReturn(postUriSpec);

        lenient().when(postUriSpec.uri(anyString()))
                .thenReturn(bodySpec);

        lenient().when(bodySpec.header(anyString(), anyString()))
                .thenReturn(bodySpec);

        lenient().when(bodySpec.contentType(any()))
                .thenReturn(bodySpec);

        lenient().when(bodySpec.body(nullable(Object.class)))
                .thenReturn(bodySpec);

        lenient().when(bodySpec.retrieve())
                .thenReturn(postResponseSpec);

        // GET
        lenient().when(restClient.get())
                .thenReturn(getUriSpec);

        lenient().when(getUriSpec.uri(anyString()))
                .thenReturn(getHeadersSpec);

        lenient().when(getHeadersSpec.header(anyString(), anyString()))
                .thenReturn(getHeadersSpec);

        lenient().when(getHeadersSpec.retrieve())
                .thenReturn(getResponseSpec);
    }

    @Test
    void deveCriarLinkDePagamentoQuandoClienteForCriadoComSucesso(){
        DTOClienteResponse clienteResponse =
                new DTOClienteResponse("cus_123", "Maria Teste","12345678900","maria@teste.com");
        DTOResponse pagamentoResponse = new DTOResponse(
                "sub_001",
                "Plano Básico",
                49.90,
                true,
                "RECURRENT",
                "https://example.com/invoice/001",
                "CREDIT_CARD",
                "MONTHLY",
                "Assinatura do plano básico",
                LocalDate.of(2026, 12, 31),
                false,
                15,
                3,
                7,
                true
        );

        when(postResponseSpec.toEntity(DTOClienteResponse.class)).
                thenReturn(ResponseEntity.ok(clienteResponse));
        when(postResponseSpec.toEntity(DTOResponse.class)).
                thenReturn(ResponseEntity.ok(pagamentoResponse));

        DTOResponse resultado = asaasService.createPaymentLink(150.0, cliente, 42);

        assertNotNull(resultado);
        verify(restClient,never()).get();

    }

    @Test
    void deveBuscarClienteExistenteQuandoCriacaoFalharMasCpfExistir(){
        DTOResponse pagamentoResponse = new DTOResponse(
                "sub_002",
                "Plano Premium",
                149.90,
                true,
                "RECURRENT",
                "https://example.com/invoice/002",
                "PIX",
                "YEARLY",
                "Assinatura do plano premium",
                LocalDate.of(2027, 8, 25),
                false,
                42,
                6,
                15,
                false
        );
        DTOClienteResponse clienteExistente=
                new DTOClienteResponse("cus_999", "Maria Teste", "12345678900", "maria@teste.com");

        // Simula falha ao tentar criar o cliente (ex: já existe, ou erro de validação do Asaas)
        when(postResponseSpec.toEntity(DTOClienteResponse.class))
                .thenThrow(new RuntimeException("Cliente já existe"));

        // O fallback busca por CPF e encontra o cliente
        when(getResponseSpec.toEntity(DTOClienteListResponse.class))
                .thenReturn(ResponseEntity.ok(new DTOClienteListResponse(List.of(clienteExistente))));

        when(postResponseSpec.toEntity(DTOResponse.class))
                .thenReturn(ResponseEntity.ok(pagamentoResponse));

        DTOResponse resultado = asaasService.createPaymentLink(150.0, cliente, 42);

        assertNotNull(resultado);
        verify(restClient, times(1)).get();

    }

    @Test
    void deveRetornarNuloQuandoClienteNaoTemCpfCriacaoFalha(){
        cliente.setCpf(null);

        lenient().when(postResponseSpec.toEntity(DTOClienteResponse.class)).
                thenThrow(new RuntimeException("Erro qualquer"));

        DTOResponse resultado = asaasService.createPaymentLink(150.0, cliente, 42);
        assertNull(resultado);
        // Sem CPF, nem tenta buscar o cliente existente
        verify(restClient, never()).get();
    }




}

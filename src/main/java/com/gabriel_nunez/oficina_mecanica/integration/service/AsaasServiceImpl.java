/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.integration.dto.DTOClienteListResponse
 *  com.gabriel_nunez.oficina_mecanica.integration.dto.DTOClienteRequest
 *  com.gabriel_nunez.oficina_mecanica.integration.dto.DTOClienteResponse
 *  com.gabriel_nunez.oficina_mecanica.integration.dto.DTOPixRequest
 *  com.gabriel_nunez.oficina_mecanica.integration.dto.DTOPixRequest$Callback
 *  com.gabriel_nunez.oficina_mecanica.integration.dto.DTOResponse
 *  com.gabriel_nunez.oficina_mecanica.integration.service.AsaasServiceImpl
 *  com.gabriel_nunez.oficina_mecanica.integration.service.IAsaasService
 *  com.gabriel_nunez.oficina_mecanica.model.Cliente
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.http.MediaType
 *  org.springframework.http.ResponseEntity
 *  org.springframework.stereotype.Service
 *  org.springframework.web.client.RestClient
 *  org.springframework.web.client.RestClient$RequestBodySpec
 */
package com.gabriel_nunez.oficina_mecanica.integration.service;

import com.gabriel_nunez.oficina_mecanica.integration.dto.DTOClienteListResponse;
import com.gabriel_nunez.oficina_mecanica.integration.dto.DTOClienteRequest;
import com.gabriel_nunez.oficina_mecanica.integration.dto.DTOClienteResponse;
import com.gabriel_nunez.oficina_mecanica.integration.dto.DTOPixRequest;
import com.gabriel_nunez.oficina_mecanica.integration.dto.DTOResponse;
import com.gabriel_nunez.oficina_mecanica.integration.service.IAsaasService;
import com.gabriel_nunez.oficina_mecanica.model.Cliente;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class AsaasServiceImpl
implements IAsaasService {
    @Value(value="${asaas.url}")
    private String baseURL;
    @Value(value="${asaas.apikey}")
    private String apiKey;
    @Value(value="${frontend.url}")
    private String frontendUrl;

    public DTOResponse createPaymentLink(Double valor_total, Cliente cliente, Integer idPedido) {
        RestClient restClient = RestClient.create();
        String customerId = this.resolverClienteAsaas(restClient, cliente);
        if (customerId == null) {
            return null;
        }
        DTOPixRequest pixRequest = new DTOPixRequest("UNDEFINED", customerId, valor_total, LocalDate.now().plusDays(1L), "App Moments", new DTOPixRequest.Callback(this.frontendUrl + "/recibo/" + idPedido, Boolean.valueOf(true)));
        try {
            ResponseEntity response = ((RestClient.RequestBodySpec)((RestClient.RequestBodySpec)restClient.post().uri(this.baseURL + "/payments", new Object[0])).header("access_token", new String[]{this.apiKey})).contentType(MediaType.APPLICATION_JSON).body(pixRequest).retrieve().toEntity(DTOResponse.class);
            return (DTOResponse)response.getBody();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String resolverClienteAsaas(RestClient restClient, Cliente cliente) {
        DTOClienteRequest clienteRequest = new DTOClienteRequest(cliente.getNome(), cliente.getCpf() != null ? cliente.getCpf().replaceAll("\\D", "") : null, cliente.getEmail(), cliente.getTelefone().replaceAll("\\D", ""), cliente.getNumero(), cliente.getComplemento(), cliente.getCep() != null ? cliente.getCep().replaceAll("\\D", "") : null);
        try {
            ResponseEntity response = ((RestClient.RequestBodySpec)((RestClient.RequestBodySpec)restClient.post().uri(this.baseURL + "/customers", new Object[0])).header("access_token", new String[]{this.apiKey})).contentType(MediaType.APPLICATION_JSON).body(clienteRequest).retrieve().toEntity(DTOClienteResponse.class);
            return ((DTOClienteResponse)response.getBody()).id();
        }
        catch (Exception e) {
            if (clienteRequest.cpfCnpj() != null) {
                return this.buscarClientePorCpf(restClient, clienteRequest.cpfCnpj());
            }
            e.printStackTrace();
            return null;
        }
    }

    private String buscarClientePorCpf(RestClient restClient, String cpfCnpj) {
        try {
            ResponseEntity response = restClient.get().uri(this.baseURL + "/customers?cpfCnpj=" + cpfCnpj, new Object[0]).header("access_token", new String[]{this.apiKey}).retrieve().toEntity(DTOClienteListResponse.class);
            List lista = ((DTOClienteListResponse)response.getBody()).data();
            if (lista != null && !lista.isEmpty()) {
                return ((DTOClienteResponse)lista.get(0)).id();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


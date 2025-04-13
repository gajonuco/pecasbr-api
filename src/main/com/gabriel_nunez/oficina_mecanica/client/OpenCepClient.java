package com.gabriel_nunez.oficina_mecanica.client;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.gabriel_nunez.oficina_mecanica.model.Endereco;

@Component
public class OpenCepClient {
    private static final String URL = "https://opencep.com/v1/%s";

    @Autowired
    private RestTemplate restTemplate;

    public Optional<Endereco> buscar(String cep) {
        try {
            return Optional.ofNullable(
                restTemplate.getForObject(String.format(URL, cep), Endereco.class)
            );
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar endereço", e);
        }
    }
}

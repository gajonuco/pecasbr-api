package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.model.Endereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class EnderecoService {

    private static final String OPEN_CEP_URL = "https://opencep.com/v1/%s";

    @Autowired
    private RestTemplate restTemplate;

    public Endereco buscarEnderecoPorCep(String cep) {
        try {
            return restTemplate.getForObject(String.format(OPEN_CEP_URL, cep), Endereco.class);
        } catch (HttpClientErrorException.NotFound e) {
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao buscar endereço. Tente novamente mais tarde.");
        }
    }
}

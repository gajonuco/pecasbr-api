package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.client.OpenCepClient;
import com.gabriel_nunez.oficina_mecanica.dto.ClienteDTO;
import com.gabriel_nunez.oficina_mecanica.mapper.ClienteMapper;
import com.gabriel_nunez.oficina_mecanica.model.Cliente;
import com.gabriel_nunez.oficina_mecanica.model.Endereco;
import com.gabriel_nunez.oficina_mecanica.model.Veiculo;
import com.gabriel_nunez.oficina_mecanica.repository.ClienteRepository;
import com.gabriel_nunez.oficina_mecanica.repository.VeiculoRepository;
import com.gabriel_nunez.oficina_mecanica.util.DocumentoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private OpenCepClient openCepClient;

    @Autowired
    private ClienteMapper clienteMapper;

    @Autowired
    private VeiculoRepository veiculoRepository;

public Cliente salvar(ClienteDTO dto) {
    String cpfCnpj = DocumentoUtils.formatarDocumento(dto.getCpfCnpj());
    verificarDuplicidade(cpfCnpj, dto.getEmail(), dto.getTelefone());

    Endereco endereco = null;
    if (dto.getCep() != null && dto.getCep().matches("\\d{8}")) {
        endereco = openCepClient.buscar(dto.getCep())
            .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado para o CEP fornecido."));
    }

    Cliente cliente = clienteMapper.toEntity(dto, endereco);

    if (dto.getPlacas() != null && !dto.getPlacas().isEmpty()) {
        List<String> erros = new ArrayList<>();
        List<Veiculo> veiculosParaAssociar = new ArrayList<>();

        for (String placa : dto.getPlacas()) {
            String placaFormatada = placa.trim().toUpperCase();
            Optional<Veiculo> optionalVeiculo = veiculoRepository.findByPlaca(placaFormatada);

            if (optionalVeiculo.isEmpty()) {
                erros.add("Placa " + placa + " não está cadastrada.");
                continue;
            }

            Veiculo veiculo = optionalVeiculo.get();

            if (veiculo.getCliente() != null) {
                if (veiculo.getCliente().getCpfCnpj().equals(cpfCnpj)) {
                    erros.add("Placa " + placa + " já está associada a este cliente.");
                } else {
                    erros.add("Placa " + placa + " já está associada a outro cliente.");
                }
                continue;
            }

            veiculo.setCliente(cliente);
            veiculosParaAssociar.add(veiculo);
        }

        cliente.setVeiculos(veiculosParaAssociar);

        if (!erros.isEmpty()) {
            throw new IllegalArgumentException(String.join(" | ", erros));
        }
    }

    return clienteRepository.save(cliente);
}



    private void verificarDuplicidade(String cpfCnpj, String email, String telefone) {
        if (clienteRepository.findByCpfCnpj(cpfCnpj).isPresent()) {
            throw new IllegalArgumentException("CPF/CNPJ já cadastrado.");
        }

        if (clienteRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com esse email.");
        }

        if (clienteRepository.findByTelefone(telefone).isPresent()) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com esse telefone.");
        }
    }
}

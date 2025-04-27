package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.client.OpenCepClient;
import com.gabriel_nunez.oficina_mecanica.dto.request.ClienteRequestDTO;
import com.gabriel_nunez.oficina_mecanica.mapper.ClienteMapper;
import com.gabriel_nunez.oficina_mecanica.model.Cliente;
import com.gabriel_nunez.oficina_mecanica.model.Endereco;
import com.gabriel_nunez.oficina_mecanica.model.Veiculo;
import com.gabriel_nunez.oficina_mecanica.repository.ClienteRepository;
import com.gabriel_nunez.oficina_mecanica.repository.VeiculoRepository;
import com.gabriel_nunez.oficina_mecanica.util.DocumentoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final OpenCepClient openCepClient;
    private final ClienteMapper clienteMapper;
    private final VeiculoRepository veiculoRepository;

    public Cliente salvar(ClienteRequestDTO dto) {
        String cpfCnpj = DocumentoUtils.formatarDocumento(dto.getCpfCnpj());
        verificarDuplicidade(cpfCnpj, dto.getTelefone());

        Endereco endereco = null;
        if (dto.getCep() != null && dto.getCep().matches("\\d{8}")) {
            endereco = openCepClient.buscar(dto.getCep())
                .orElseThrow(() -> new IllegalArgumentException("Endereço não encontrado para o CEP fornecido."));
        }

        Cliente cliente = clienteMapper.toEntity(dto, endereco);

        if (dto.getPlacas() != null && !dto.getPlacas().isEmpty()) {
            List<Veiculo> veiculosParaAssociar = new ArrayList<>();

            for (String placa : dto.getPlacas()) {
                Optional<Veiculo> optionalVeiculo = veiculoRepository.findByPlaca(placa.trim().toUpperCase());
                optionalVeiculo.ifPresent(veiculo -> {
                    veiculo.setCliente(cliente);
                    veiculosParaAssociar.add(veiculo);
                });
            }

            cliente.setVeiculos(veiculosParaAssociar);
        }

        return clienteRepository.save(cliente);
    }

    private void verificarDuplicidade(String cpfCnpj, String telefone) {
        if (clienteRepository.findByCpfCnpj(cpfCnpj).isPresent()) {
            throw new IllegalArgumentException("CPF/CNPJ já cadastrado.");
        }

        if (clienteRepository.findByTelefone(telefone).isPresent()) {
            throw new IllegalArgumentException("Já existe um cliente cadastrado com esse telefone.");
        }
    }
}

package com.gabriel_nunez.oficina_mecanica.service;

import com.gabriel_nunez.oficina_mecanica.exception.DuplicatePlacaException;
import com.gabriel_nunez.oficina_mecanica.model.Veiculo;
import com.gabriel_nunez.oficina_mecanica.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static com.gabriel_nunez.oficina_mecanica.util.PlacaUtils.formatarPlaca;


@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Transactional
    public Veiculo salvar(Veiculo veiculo) {
        veiculo.setPlaca(formatarPlaca(veiculo.getPlaca()));
    
        if (veiculoRepository.existsByPlaca(veiculo.getPlaca())) {
            throw new DuplicatePlacaException("Já existe um veículo cadastrado com essa placa.");
        }
    
        return veiculoRepository.save(veiculo);
    }
    
}

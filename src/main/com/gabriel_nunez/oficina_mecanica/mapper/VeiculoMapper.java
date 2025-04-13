package com.gabriel_nunez.oficina_mecanica.mapper;

import com.gabriel_nunez.oficina_mecanica.model.Veiculo;
import com.gabriel_nunez.oficina_mecanica.dto.VeiculoDTO;

public class VeiculoMapper {

    public static Veiculo toEntity(VeiculoDTO dto) {
        Veiculo v = new Veiculo();
        v.setTipo(dto.getTipo());
        v.setPlaca(dto.getPlaca());
        v.setMarca(dto.getMarca());
        v.setModelo(dto.getModelo());
        v.setAno(dto.getAno());
        v.setQuilometragem(dto.getQuilometragem());
        return v;
    }

    public static VeiculoDTO toDTO(Veiculo v) {
        return VeiculoDTO.builder()
            .tipo(v.getTipo())
            .placa(v.getPlaca())
            .marca(v.getMarca())
            .modelo(v.getModelo())
            .ano(v.getAno())
            .quilometragem(v.getQuilometragem())
            .build();
    }
}


package com.gabriel_nunez.oficina_mecanica.mapper;

import com.gabriel_nunez.oficina_mecanica.dto.request.VeiculoRequestDTO;
import com.gabriel_nunez.oficina_mecanica.dto.response.VeiculoResponseDTO;
import com.gabriel_nunez.oficina_mecanica.model.Veiculo;
import org.springframework.stereotype.Component;

@Component
public class VeiculoMapper {

    public Veiculo toEntity(VeiculoRequestDTO dto) {
        Veiculo v = new Veiculo();
        v.setTipo(dto.getTipo());
        v.setPlaca(dto.getPlaca());
        v.setMarca(dto.getMarca());
        v.setModelo(dto.getModelo());
        v.setAno(dto.getAno());
        v.setQuilometragem(dto.getQuilometragem());
        return v;
    }

    public VeiculoResponseDTO toResponseDTO(Veiculo v) {
        return VeiculoResponseDTO.builder()
            .id(v.getId())
            .tipo(v.getTipo())
            .placa(v.getPlaca())
            .marca(v.getMarca())
            .modelo(v.getModelo())
            .ano(v.getAno())
            .quilometragem(v.getQuilometragem())
            .build();
    }
}

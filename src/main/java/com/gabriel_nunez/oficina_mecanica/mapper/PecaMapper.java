package com.gabriel_nunez.oficina_mecanica.mapper;

import org.springframework.stereotype.Component;

import com.gabriel_nunez.oficina_mecanica.dto.PecaDTO;
import com.gabriel_nunez.oficina_mecanica.model.Peca;

@Component
public class PecaMapper {

    public static Peca toEntity(PecaDTO dto) {
        Peca peca = new Peca();
        peca.setNome(dto.getNome());
        peca.setSku(dto.getSku().trim().toUpperCase());
        peca.setMarca(dto.getMarca());
        peca.setPreco(dto.getPreco());
        peca.setQuantidadeEmEstoque(dto.getQuantidadeEmEstoque());
        peca.setDataVencimento(dto.getDataVencimento());
        return peca;
    }

    public static PecaDTO toDTO(Peca peca) {
        return PecaDTO.builder()
            .nome(peca.getNome())
            .sku(peca.getSku())
            .marca(peca.getMarca())
            .preco(peca.getPreco())
            .quantidadeEmEstoque(peca.getQuantidadeEmEstoque())
            .dataVencimento(peca.getDataVencimento())
            .build();
    }
}

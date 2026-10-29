package com.gajonuco.pecasbr.dto;

import com.gajonuco.pecasbr.model.Peca;

public record PecaConsultaDTO(
        Integer id,
        String nome,
        String detalhe,
        double preco,
        double precoPromo,
        boolean emPromocao,
        String categoria,
        boolean disponivel,
        boolean prontaEntrega,
        boolean destaque,
        String statusEstoque,
        String imagemPrincipal
) {
    public static PecaConsultaDTO from (Peca peca){
        boolean emPromocao = peca.getPrecoPromo() > 0 && peca.getPrecoPromo() < peca.getPreco();

        return new PecaConsultaDTO(
                peca.getId(),
                peca.getNome(),
                peca.getDetalhe(),
                peca.getPreco(),
                peca.getPrecoPromo(),
                emPromocao,
                peca.getCategoriaPeca() != null
                        ? peca.getCategoriaPeca().getNome()
                        : null,
                peca.getDisponivel() > 0,
                peca.getProntaEntrega() != null
                        && peca.getProntaEntrega() > 0,
                peca.getDestaque() != null
                        && peca.getDestaque() > 0,
                peca.getStatusEstoque(),
                peca.getImagemPrincipal()
        );
    }
}
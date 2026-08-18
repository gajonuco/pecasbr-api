/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.dto.PecaVariacaoDTO
 *  com.gabriel_nunez.oficina_mecanica.dto.SalvarVariacoesDTO
 */
package com.gabriel_nunez.oficina_mecanica.dto;

import com.gabriel_nunez.oficina_mecanica.dto.PecaVariacaoDTO;
import java.util.List;

public class SalvarVariacoesDTO {
    private Boolean corUnica;
    private Boolean tamanhoUnico;
    private List<PecaVariacaoDTO> variacoes;

    public Boolean getCorUnica() {
        return this.corUnica;
    }

    public void setCorUnica(Boolean corUnica) {
        this.corUnica = corUnica;
    }

    public Boolean getTamanhoUnico() {
        return this.tamanhoUnico;
    }

    public void setTamanhoUnico(Boolean tamanhoUnico) {
        this.tamanhoUnico = tamanhoUnico;
    }

    public List<PecaVariacaoDTO> getVariacoes() {
        return this.variacoes;
    }

    public void setVariacoes(List<PecaVariacaoDTO> variacoes) {
        this.variacoes = variacoes;
    }
}


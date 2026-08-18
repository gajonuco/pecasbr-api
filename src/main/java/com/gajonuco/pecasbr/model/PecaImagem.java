/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.gajonuco.pecasbr.model.Peca
 *  com.gajonuco.pecasbr.model.PecaImagem
 *  jakarta.persistence.Column
 *  jakarta.persistence.Entity
 *  jakarta.persistence.FetchType
 *  jakarta.persistence.GeneratedValue
 *  jakarta.persistence.GenerationType
 *  jakarta.persistence.Id
 *  jakarta.persistence.JoinColumn
 *  jakarta.persistence.ManyToOne
 *  jakarta.persistence.Table
 */
package com.gajonuco.pecasbr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gajonuco.pecasbr.model.Peca;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="tbl_peca_imagem")
public class PecaImagem {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_imagem")
    private Integer id;
    @Column(name="link_imagem", length=255, nullable=false)
    private String linkImagem;
    @Column(name="ordem")
    private Integer ordem;
    @Column(name="principal")
    private Integer principal;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="id_peca", nullable=false)
    @JsonIgnore
    private Peca peca;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLinkImagem() {
        return this.linkImagem;
    }

    public void setLinkImagem(String linkImagem) {
        this.linkImagem = linkImagem;
    }

    public Integer getOrdem() {
        return this.ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Integer getPrincipal() {
        return this.principal;
    }

    public void setPrincipal(Integer principal) {
        this.principal = principal;
    }

    public Peca getPeca() {
        return this.peca;
    }

    public void setPeca(Peca peca) {
        this.peca = peca;
    }
}


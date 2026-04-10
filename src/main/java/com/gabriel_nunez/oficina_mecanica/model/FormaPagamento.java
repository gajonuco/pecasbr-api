/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gabriel_nunez.oficina_mecanica.model.FormaPagamento
 *  jakarta.persistence.Column
 *  jakarta.persistence.Entity
 *  jakarta.persistence.GeneratedValue
 *  jakarta.persistence.GenerationType
 *  jakarta.persistence.Id
 *  jakarta.persistence.Table
 */
package com.gabriel_nunez.oficina_mecanica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tbl_formapgto")
public class FormaPagamento {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="num_seq")
    private int numSeq;
    @Column(name="descricao", length=40, nullable=false)
    private String descricao;
    @Column(name="visivel", nullable=false)
    private int visivel;
    @Column(name="retencao", nullable=false)
    private double retencao;

    public int getNumSeq() {
        return this.numSeq;
    }

    public void setNumSeq(int numSeq) {
        this.numSeq = numSeq;
    }

    public String getDescricao() {
        return this.descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getVisivel() {
        return this.visivel;
    }

    public void setVisivel(int visivel) {
        this.visivel = visivel;
    }

    public double getRetencao() {
        return this.retencao;
    }

    public void setRetencao(double retencao) {
        this.retencao = retencao;
    }
}


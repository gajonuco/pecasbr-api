package com.gabriel_nunez.oficina_mecanica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_frete")
public class Frete {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "prefixo", length = 10, nullable = false, unique = true)
	private String prefixo;

	@Column(name = "descricao", length = 100)
	private String descricao;
	
	@Column(name = "valor")
	private double valor;
	
	@Column(name = "disponivel")
	private int disponivel;

	
	public String toString() {
		return this.prefixo;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPrefixo() {
		return prefixo;
	}

	public void setPrefixo(String prefixo) {
		this.prefixo = prefixo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getDisponivel() {
		return disponivel;
	}

	public void setDisponivel(int disponivel) {
		this.disponivel = disponivel;
	}
	
	


}

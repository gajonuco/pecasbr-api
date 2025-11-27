package com.gabriel_nunez.oficina_mecanica.model;

import jakarta.persistence.*;
import java.time.LocalDate;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tbl_pedido")
public class Pedido {

	public static final int NOVO_PEDIDO   = 1;
	public static final int PAGO          = 2;
	public static final int EM_TRANSPORTE = 3;
	public static final int ENTREGUE      = 4;
	public static final int POS_VENDA     = 5;
	public static final int FINALIZADO    = 6;
    public static final int CANCELADO     = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer id;

    @Column(name = "data_pedido")
    private LocalDate dataPedido;

    @Column(name = "valor_total")
    private double valorTotal;

    @Column(name = "observacoes", length = 500)
    private String observacoes;

    @Column(name = "status", nullable = false)
    private int status;

    	@Column(name = "valor_frete")
	private double valorFrete;
	
	@Column(name = "retirar")
	private int    retirar;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("pedido")
    private List<ItemPedido> itensPedido;

    	public Pedido() {
		super();
	}
	
	public Pedido(int id, LocalDate data, String nomeCliente, double valorTotal, double frete, int retirar, String observacao, int status) {
		this.id = id;
		this.dataPedido = data;
		Cliente cliente = new Cliente();
		cliente.setNome(nomeCliente);
		this.cliente = cliente;
		this.valorTotal = valorTotal;
		this.valorFrete = frete;
		this.retirar = retirar;
		this.observacoes = observacao;
		this.status = status;
	}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItensPedido() {
        return itensPedido;
    }

    public void setItensPedido(List<ItemPedido> itensPedido) {
        this.itensPedido = itensPedido;
    }

    public double getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(double valorFrete) {
        this.valorFrete = valorFrete;
    }

    public int getRetirar() {
        return retirar;
    }

    public void setRetirar(int retirar) {
        this.retirar = retirar;
    }

    

    
}

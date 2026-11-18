package com.gajonuco.pecasbr.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tbl_pedido")
public class Pedido {

    public static final int NOVO_PEDIDO = 1;
    public static final int PAGO = 2;
    public static final int EM_TRANSPORTE = 3;
    public static final int ENTREGUE = 4;
    public static final int POS_VENDA = 5;
    public static final int FINALIZADO = 6;
    public static final int CANCELADO = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Integer id;

    @Column(name = "data_pedido")
    private LocalDate dataPedido;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Column(name = "observacoes", length = 500)
    private String observacoes;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "valor_frete")
    private BigDecimal valorFrete;

    @Column(name = "retirar")
    private int retirar;

    @Column(name = "link_pagamento", length = 500)
    private String linkPagamento;

    @Column(name = "asaas_payment_id")
    private String asaasPaymentId;

    @ManyToOne
    @JoinColumn(name = "id_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_endereco_entrega")
    private Endereco enderecoEntrega;

    public Endereco getEnderecoEntrega() { return enderecoEntrega; }
    public void setEnderecoEntrega(Endereco enderecoEntrega) { this.enderecoEntrega = enderecoEntrega; }

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("pedido")
    private List<ItemPedido> itensPedido;

    public Pedido() {
        super();
    }

    public static Pedido novoPedido(
            Cliente cliente,
            Endereco enderecoEntrega,
            boolean retirar,
            BigDecimal valorFrete,
            String observacoes,
            List<ItemPedido> itens){

        Objects.requireNonNull(cliente, "O cliente é obrigatório.");
        Objects.requireNonNull(itens, "A lista de itens é obrigatória.");

        if (!retirar) {
            Objects.requireNonNull(enderecoEntrega,
                    "O endereço de entrega é obrigatório para pedidos com entrega.");
        }

        Pedido pedido = new Pedido();

        pedido.cliente = cliente;
        pedido.enderecoEntrega = retirar ? null : enderecoEntrega;
        pedido.observacoes = observacoes;
        pedido.retirar = retirar ? 1 : 0;
        pedido.valorFrete = retirar || valorFrete == null
                ? BigDecimal.ZERO
                : valorFrete;
        pedido.dataPedido = LocalDate.now();

        pedido.itensPedido = new ArrayList<>();

        for (ItemPedido item : itens) {
            Objects.requireNonNull(item, "A lista não pode conter itens nulos.");

            item.setPedido(pedido);
            pedido.itensPedido.add(item);
        }
        return pedido;
    }

    public Pedido(int id, LocalDate data, String nomeCliente, BigDecimal valorTotal, BigDecimal frete, int retirar,
            String observacao, int status) {
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

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
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

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }

    public int getRetirar() {
        return retirar;
    }

    public void setRetirar(int retirar) {
        this.retirar = retirar;
    }

    public String getLinkPagamento() {
        return linkPagamento;
    }

    public void setLinkPagamento(String linkPagamento) {
        this.linkPagamento = linkPagamento;
    }
    public String getAsaasPaymentId() {
        return asaasPaymentId;
    }
    public void setAsaasPaymentId(String asaasPaymentId) {
        this.asaasPaymentId = asaasPaymentId;
    }
}

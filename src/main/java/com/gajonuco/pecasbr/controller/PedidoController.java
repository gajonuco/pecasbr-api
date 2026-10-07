/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.gajonuco.pecasbr.controller.PedidoController
 *  com.gajonuco.pecasbr.dto.FiltroPedidoDTO
 *  com.gajonuco.pecasbr.dto.VendasPorDataDTO
 *  com.gajonuco.pecasbr.model.Cliente
 *  com.gajonuco.pecasbr.model.Pedido
 *  com.gajonuco.pecasbr.service.IClienteService
 *  com.gajonuco.pecasbr.service.IPedidoService
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.http.ResponseEntity
 *  org.springframework.web.bind.annotation.CrossOrigin
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PatchMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.PutMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.gajonuco.pecasbr.controller;

import com.gajonuco.pecasbr.dto.CriarPedidoDTO;
import com.gajonuco.pecasbr.dto.FiltroPedidoDTO;
import com.gajonuco.pecasbr.dto.ItemPedidoDTO;
import com.gajonuco.pecasbr.dto.VendasPorDataDTO;
import com.gajonuco.pecasbr.exception.EnderecoNaoEncontradoException;
import com.gajonuco.pecasbr.model.*;
import com.gajonuco.pecasbr.service.IClienteService;
import com.gajonuco.pecasbr.service.IEnderecoService;
import com.gajonuco.pecasbr.service.IPedidoService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(value={"*"})
@RestController
public class PedidoController {

    private final IPedidoService service;
    private final IClienteService cliService;
    private final IEnderecoService enderecoService;
    public PedidoController(IPedidoService service, IClienteService cliservice, IEnderecoService enderecoService){
        this.service = service;
        this.cliService = cliservice;
        this.enderecoService = enderecoService;
    }

    @PostMapping("/pedido")
    public ResponseEntity<Pedido> inserirNovoPedido(@Valid @RequestBody CriarPedidoDTO dados, Authentication authentication) {
        Cliente cliente = resolverCliente(dados, authentication);
        if (cliente == null) {
            return ResponseEntity.badRequest().build();
        }

        Endereco enderecoEntrega = null;
        if (!dados.retirar()) {
            enderecoEntrega = resolverEndereco(dados, cliente);
            if (enderecoEntrega == null) {
                return ResponseEntity.badRequest().build();
            }
        }

        Pedido novo = new Pedido();
        novo.setCliente(cliente);
        novo.setEnderecoEntrega(enderecoEntrega);
        novo.setObservacoes(dados.observacoes());
        novo.setRetirar(dados.retirar() ? 1 : 0);
        novo.setDataPedido(LocalDate.now());
        novo.setItensPedido(montarItens(dados.itens()));

        Pedido criado = service.inserirPedido(novo);
        if (criado == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }

    private Cliente resolverCliente(CriarPedidoDTO dados, Authentication authentication) {
        if (authentication != null && ehClienteLogado(authentication)) {
            return cliService.buscarPeloEmail(authentication.getName());
        }
        if (dados.cliente() == null) {
            return null;
        }
        Cliente guest = new Cliente();
        guest.setNome(dados.cliente().nome());
        guest.setEmail(dados.cliente().email());
        guest.setCpf(dados.cliente().cpf());
        guest.setTelefone(dados.cliente().telefone());
        guest.setDataNasc(dados.cliente().dataNasc());
        return cliService.atualizarDados(guest);
    }

    private boolean ehClienteLogado(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_CLIENTE"));
    }

    private Endereco resolverEndereco(CriarPedidoDTO dados, Cliente cliente) {
        try {
            if (dados.idEndereco() != null) {
                return enderecoService.buscarPorIdDoCliente(cliente, dados.idEndereco());
            }
            if (dados.enderecoNovo() != null) {
                return enderecoService.criar(cliente, dados.enderecoNovo());
            }
        } catch (EnderecoNaoEncontradoException e) {
            return null;
        }
        return null;
    }

    private List<ItemPedido> montarItens(List<ItemPedidoDTO> itensDTO) {
        List<ItemPedido> itens = new ArrayList<>();
        for (ItemPedidoDTO dto : itensDTO) {
            Peca peca = new Peca();
            peca.setId(dto.idPeca());

            ItemPedido item = new ItemPedido();
            item.setPeca(peca);
            item.setQtdtItem(dto.quantidade());
            item.setCorEscolhida(dto.corEscolhida());
            item.setTamanhoEscolhido(dto.tamanhoEscolhido());
            if (dto.idVariacao() != null) {
                PecaVariacao variacao = new PecaVariacao();
                variacao.setId(dto.idVariacao());
                item.setVariacao(variacao);
            }
            itens.add(item);
        }
        return itens;
    }

    @PostMapping(value={"/pedido/filtrar"})
    public ResponseEntity<ArrayList<Pedido>> buscarTodos(@RequestBody FiltroPedidoDTO parametros) {
        return ResponseEntity.ok(this.service.filtrarPorVariosCriterios(parametros));
    }

    @PatchMapping(value={"/pedido/{id}"})
    public ResponseEntity<Pedido> mudarStatus(@PathVariable(name="id") int id, @RequestParam(name="status") int status) {
        try {
            Pedido pedido = this.service.mudarStatus(id, status);
            if (pedido != null) {
                return ResponseEntity.ok(pedido);
            }
            return ResponseEntity.badRequest().build();
        }
        catch (Exception e) {
            return ResponseEntity.status((int)500).build();
        }
    }

    @GetMapping("/pedido/search/{id}")
    public ResponseEntity<Pedido> recuperarPedido(@PathVariable(name="id") int id, Authentication authentication) {
        Pedido pedido = service.buscarPeloId(id);
        if(pedido == null){
            return ResponseEntity.notFound().build();
        }
        if(!podeAcessar(pedido, authentication)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(pedido);
    }
    @GetMapping("/pedido/meus")
    public ResponseEntity<List<Pedido>> meusPedidos(Authentication authentication){
        Cliente cliente = cliService.buscarPeloEmail(authentication.getName());
        if(cliente == null){
            return  ResponseEntity.ok(List.of());
        }
        return  ResponseEntity.ok(service.buscarPorCliente(cliente));
    }

    private boolean podeAcessar(Pedido pedido,Authentication authentication){
        boolean ehStaff = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN") || a.getAuthority().equals("ROLE_VENDEDOR"));
        if (ehStaff){
            return true;
        }
        Cliente dono = pedido.getCliente();
        return  dono != null && dono.getEmail() != null && dono.getEmail().equals(authentication.getName());
    }


    @GetMapping(value={"/pedido/recentes"})
    public ResponseEntity<List<VendasPorDataDTO>> recuperarUltimasVendas(@RequestParam(value="inicio") String dataIni, @RequestParam(value="fim") String dataFim) {
        LocalDate inicio = LocalDate.parse(dataIni);
        LocalDate fim = LocalDate.parse(dataFim);
        return ResponseEntity.ok(this.service.recuperarTotaisUltimaSemana(inicio, fim));
    }

    @PutMapping(value={"/pedido"})
    public ResponseEntity<Pedido> atualizarPedido(@RequestBody Pedido pedido) {
        try {
            Pedido atualizado = this.service.atualizarPedido(pedido);
            if (atualizado == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(atualizado);
        }
        catch (Exception ex) {
            System.out.println("Erro ao atualizar ");
            ex.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}


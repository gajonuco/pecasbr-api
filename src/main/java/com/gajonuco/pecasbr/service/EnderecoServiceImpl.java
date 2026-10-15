package com.gajonuco.pecasbr.service;

import com.gajonuco.pecasbr.dao.EnderecoDAO;
import com.gajonuco.pecasbr.dto.EnderecoDTO;
import com.gajonuco.pecasbr.exception.EnderecoNaoEncontradoException;
import com.gajonuco.pecasbr.model.Cliente;
import com.gajonuco.pecasbr.model.Endereco;
import org.apache.logging.log4j.util.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Component
public class EnderecoServiceImpl implements IEnderecoService{

    private final EnderecoDAO dao;

    public EnderecoServiceImpl(EnderecoDAO dao){
        this.dao = dao;
    }
    @Override
    public List<Endereco> listarPorCliente(Cliente cliente) {
        return dao.findByClienteId(cliente.getId());
    }

    @Override
    public Endereco criar(Cliente cliente, EnderecoDTO dados) {
        Endereco endereco = new Endereco();
        preencher(endereco,dados);
        endereco.setCliente(cliente);

        boolean primeiroEndereco = dao.findByClienteId(cliente.getId()).isEmpty();
        endereco.setPrincipal(primeiroEndereco);

        return  dao.save(endereco);
    }

    @Override
    public Endereco atualizar(Cliente cliente, int idEndereco, EnderecoDTO dados) {
        Endereco endereco = buscarDoCliente(cliente, idEndereco);
        preencher(endereco, dados);
        return dao.save(endereco);
    }

    public void remover(Cliente cliente, int idEndereco) {
        Endereco endereco = buscarDoCliente(cliente, idEndereco);
        boolean eraPrincipal = endereco.isPrincipal();

        dao.delete(endereco);

        if(eraPrincipal){
            dao.findByClienteId(cliente.getId()).stream()
                    .min(Comparator.comparing(Endereco::getId))
                    .ifPresent(maisAntigo -> {
                        maisAntigo.setPrincipal(true);
                        dao.save(maisAntigo);
                    });
        }
    }

    public Endereco marcarComoPrincipal(Cliente cliente, int idEndereco) {
        Endereco novoPrincipal = buscarDoCliente(cliente, idEndereco);

        dao.findByClienteId(cliente.getId()).forEach(endereco -> {
            if(endereco.isPrincipal() && endereco.getId() != idEndereco){
                endereco.setPrincipal(false);
                dao.save(endereco);
            }
        });
        novoPrincipal.setPrincipal(true);
        return  dao.save(novoPrincipal);
    }

    private Endereco buscarDoCliente(Cliente cliente, int idEndereco) {
        return dao.findByIdAndClienteId(idEndereco, cliente.getId()).orElseThrow(EnderecoNaoEncontradoException::new);
    }

    private void preencher(Endereco endereco, EnderecoDTO dados){
        endereco.setApelido(dados.apelido());
        endereco.setCep(dados.cep());
        endereco.setLogradouro(dados.logradouro());
        endereco.setNumero(dados.numero());
        endereco.setComplemento(dados.complemento());
        endereco.setBairro(dados.bairro());
        endereco.setCidade(dados.cidade());
        endereco.setEstado(dados.estado());
    }

    public Endereco buscarPorIdDoCliente(Cliente cliente, int idEndereco) {
        return dao.findByIdAndClienteId(idEndereco, cliente.getId())
                .orElseThrow(EnderecoNaoEncontradoException::new);
    }

}

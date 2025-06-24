(() => {
    const token = sessionStorage.getItem('token');
    let stompClientPainel;

    if (!token) {
        alert("Você precisa estar logado.");
        window.location.href = "/html/login.html";
        return;
    }

    function formatarDataHora(isoString) {
        const data = new Date(isoString);
        return `${String(data.getDate()).padStart(2, '0')}/${
            String(data.getMonth() + 1).padStart(2, '0')}/${data.getFullYear()} ${
            String(data.getHours()).padStart(2, '0')}:${String(data.getMinutes()).padStart(2, '0')}`;
    }

    function criarItemSolicitacao(s) {
        const item = document.createElement('li');
        item.setAttribute('data-id', s.id);
        item.classList.add('solicitacao');

        const dataHora = s.dataHoraSelecionada || s.dataHora || new Date().toISOString();
        const descricao = s.descricao || s.nomeServico || "Serviço";
        const cliente = s.nomeCliente || `Cliente #${s.clienteUsuarioId}`;
        const precoValido = s.preco !== undefined && !isNaN(parseFloat(s.preco));
        const tipoServicoValido = !!s.nomeTipoServico;

        let estado = 'recebido';

        if (precoValido && tipoServicoValido) {
            estado = 'finalizado';
            const precoFormatado = parseFloat(s.preco).toFixed(2);
            item.textContent = `✅ O cliente ${s.nomeCliente} que solicitou ${descricao}, escolheu o ${s.nomeTipoServico} no valor de R$ ${precoFormatado} em ${formatarDataHora(dataHora)}`;
            item.classList.add('oferta-recebida');

            const botaoExcluirOferta = document.createElement('button');
            botaoExcluirOferta.textContent = 'Excluir';
            botaoExcluirOferta.style.marginLeft = '10px';

            botaoExcluirOferta.addEventListener('click', () => {
                if (confirm('Tem certeza que deseja excluir esta oferta selecionada?')) {
                    axios.delete(`/api/ofertas-selecionadas/${s.id}`, {
                        headers: { Authorization: `Bearer ${token}` }
                    })
                    .then(() => {
                        item.remove();
                        const historico = JSON.parse(localStorage.getItem('solicitacoesSalvas')) || [];
                        const atualizado = historico.filter(sol => sol.id !== s.id);
                        localStorage.setItem('solicitacoesSalvas', JSON.stringify(atualizado));
                        alert("Oferta removida com sucesso.");
                    })
                    .catch(err => {
                        console.error("Erro ao excluir oferta:", err);
                        alert("Erro ao excluir a oferta.");
                    });
                }
            });

            item.appendChild(botaoExcluirOferta);

        } else if (s.aguardandoEscolha) {
            estado = 'aguardando';
            item.textContent = `🕒 Aguardando escolha do cliente para o serviço: ${descricao}`;
            item.classList.add('aguardando-escolha');

        } else {
            item.textContent = `⚠️ ${cliente} solicitou: ${descricao} em ${formatarDataHora(dataHora)}`;

            const botaoDeletar = document.createElement('button');
            botaoDeletar.textContent = 'Excluir';
            botaoDeletar.style.marginLeft = '10px';

            const botaoEnviar = document.createElement('button');
            botaoEnviar.textContent = 'Enviar Ofertas';
            botaoEnviar.style.marginLeft = '10px';

            botaoDeletar.addEventListener('click', () => {
                if (confirm('Tem certeza que deseja excluir esta solicitação?')) {
                    axios.delete(`/api/solicitacoes/${s.id}`, {
                        headers: { Authorization: `Bearer ${token}` }
                    })
                    .then(() => {
                        item.remove();
                        const historico = JSON.parse(localStorage.getItem('solicitacoesSalvas')) || [];
                        const atualizado = historico.filter(sol => sol.id !== s.id);
                        localStorage.setItem('solicitacoesSalvas', JSON.stringify(atualizado));

                        const excluidos = JSON.parse(localStorage.getItem('solicitacoesExcluidas')) || [];
                        if (!excluidos.includes(s.id)) {
                            excluidos.push(s.id);
                            localStorage.setItem('solicitacoesExcluidas', JSON.stringify(excluidos));
                        }
                    })
                    .catch(err => {
                        console.error('Erro ao deletar solicitação:', err);
                        alert('Não foi possível deletar a solicitação.');
                    });
                }
            });

            botaoEnviar.addEventListener('click', () => {
                axios.post(`/api/solicitacoes/${s.id}/enviar-ofertas`, null, {
                    headers: { Authorization: `Bearer ${token}` }
                })
                .then(() => {
                    s.aguardandoEscolha = true;
                    const historico = JSON.parse(localStorage.getItem('solicitacoesSalvas')) || [];
                    const atualizados = historico.map(sol => sol.id === s.id ? s : sol);
                    localStorage.setItem('solicitacoesSalvas', JSON.stringify(atualizados));

                    item.remove();
                    const novo = criarItemSolicitacao(s);
                    document.getElementById('lista-notificacoes').appendChild(novo);
                })
                .catch(err => {
                    console.error('Erro ao enviar ofertas:', err);
                    alert('Erro ao enviar ofertas para o cliente.');
                });
            });

            item.appendChild(botaoDeletar);
            item.appendChild(botaoEnviar);
        }

        // Atualiza localStorage com estado atual
        const historico = JSON.parse(localStorage.getItem('solicitacoesSalvas')) || [];
        const atualizada = { ...s, estado };
        const atualizado = historico.filter(sol => sol.id !== s.id);
        atualizado.push(atualizada);
        localStorage.setItem('solicitacoesSalvas', JSON.stringify(atualizado));

        return item;
    }

    function carregarOfertasSelecionadasSalvas() {
        const clienteId = sessionStorage.getItem('clienteId');
        if (!clienteId) return;

        axios.get(`/api/ofertas-selecionadas/cliente/${clienteId}`, {
            headers: { Authorization: `Bearer ${token}` }
        }).then(res => {
            const historico = JSON.parse(localStorage.getItem('solicitacoesSalvas')) || [];

            res.data.forEach(oferta => {
                const dadosAtualizados = {
                    id: oferta.idSolicitacao || oferta.id,
                    nomeCliente: oferta.nomeCliente,
                    nomeTipoServico: oferta.nomeTipoServico,
                    descricao: oferta.descricao,
                    preco: oferta.preco,
                    dataHoraSelecionada: oferta.dataHoraSelecionada
                };

                const itemAntigo = document.querySelector(`li[data-id='${dadosAtualizados.id}']`);
                if (itemAntigo) itemAntigo.remove();

                const indexExistente = historico.findIndex(sol => sol.id === dadosAtualizados.id);
                if (indexExistente !== -1) {
                    historico[indexExistente] = dadosAtualizados;
                } else {
                    historico.push(dadosAtualizados);
                }

                document.getElementById('lista-notificacoes').appendChild(criarItemSolicitacao(dadosAtualizados));
            });

            localStorage.setItem('solicitacoesSalvas', JSON.stringify(historico));
        }).catch(err => console.error('Erro ao carregar ofertas selecionadas:', err));
    }

    function carregarSolicitacoesExistentes() {
        axios.get('/api/solicitacoes', {
            headers: { Authorization: `Bearer ${token}` }
        })
        .then(res => {
            const listaDOM = document.getElementById('lista-notificacoes');
            listaDOM.innerHTML = '';

            const localSalvas = JSON.parse(localStorage.getItem('solicitacoesSalvas')) || [];
            const idsExcluidos = JSON.parse(localStorage.getItem('solicitacoesExcluidas')) || [];

            const mescladas = res.data
                .filter(s => !idsExcluidos.includes(s.id))
                .map(solicitacao => {
                    const local = localSalvas.find(sol => sol.id === solicitacao.id);
                    return local ? { ...solicitacao, ...local } : solicitacao;
                });

            mescladas.forEach(s => listaDOM.appendChild(criarItemSolicitacao(s)));
            localStorage.setItem('solicitacoesSalvas', JSON.stringify(mescladas));
        })
        .catch(err => {
            console.error('Erro ao buscar solicitações:', err);
            alert('Não foi possível carregar as solicitações.');
        });
    }

    function connectWebSocketPainel() {
        const socket = new SockJS('/ws');
        stompClientPainel = Stomp.over(socket);

        stompClientPainel.connect({ Authorization: 'Bearer ' + token }, () => {
            stompClientPainel.subscribe("/topic/notifications", message => {
                const s = JSON.parse(message.body);
                const excluidos = JSON.parse(localStorage.getItem('solicitacoesExcluidas')) || [];
                if (excluidos.includes(s.id)) return;

                // Enriquecer com dados anteriores se faltando
                const historico = JSON.parse(localStorage.getItem('solicitacoesSalvas')) || [];
                const anterior = historico.find(h => h.id === s.id) || {};

                s.nomeCliente = s.nomeCliente || anterior.nomeCliente || `Cliente #${s.clienteUsuarioId}`;
                s.descricao = s.descricao || anterior.descricao || 'Serviço';
                s.nomeTipoServico = s.nomeTipoServico || anterior.nomeTipoServico;
                s.preco = s.preco !== undefined ? s.preco : anterior.preco;
                s.dataHoraSelecionada = s.dataHoraSelecionada || anterior.dataHoraSelecionada;

                const itemAntigo = document.querySelector(`li[data-id='${s.id}']`);
                if (itemAntigo) itemAntigo.remove();

                const itemNovo = criarItemSolicitacao(s);
                document.getElementById('lista-notificacoes').appendChild(itemNovo);

                const atualizado = historico.filter(sol => sol.id !== s.id);
                atualizado.push({ ...s });
                localStorage.setItem('solicitacoesSalvas', JSON.stringify(atualizado));
            });
        });
    }

    document.addEventListener("DOMContentLoaded", () => {
        carregarSolicitacoesExistentes();
        carregarOfertasSelecionadasSalvas();
        connectWebSocketPainel();
    });

    document.getElementById("limpar-historico-espera").addEventListener("click", () => {
        if (confirm("Deseja realmente remover todas as solicitações em estado de 'aguardando escolha'?")) {
            let historico = JSON.parse(localStorage.getItem('solicitacoesSalvas')) || [];
            historico = historico.filter(s => s.estado !== 'aguardando');
            localStorage.setItem('solicitacoesSalvas', JSON.stringify(historico));
            document.querySelectorAll("li.solicitacao.aguardando-escolha").forEach(el => el.remove());
        }
    });
})();

document.addEventListener('DOMContentLoaded', () => {
    const token = sessionStorage.getItem('token');
    let stompClientCliente;
    let orcamentoSelecionado = null;

    if (!token || sessionStorage.getItem('tipo') !== 'CLIENTE') {
        alert("Você precisa estar logado como cliente.");
        window.location.href = "/html/login.html";
        return;
    }

    // Carrega os tipos de serviços no select
    axios.get('/tipos-servicos', {
        headers: { Authorization: `Bearer ${token}` }
    }).then(response => {
        const select = document.getElementById('servicos');
        select.innerHTML = '';
        response.data.forEach(servico => {
            const option = document.createElement('option');
            option.value = servico.id;
            option.textContent = servico.nome;
            select.appendChild(option);
        });
    }).catch(error => {
        console.error('Erro ao carregar tipos de serviço:', error);
        alert("Erro ao carregar os tipos de serviço.");
    });

    // Ao clicar no botão de buscar orçamentos
    document.getElementById('enviar-btn').addEventListener('click', () => {
        const tipoServicoId = document.getElementById('servicos').value;

        axios.get(`/orcamentos-servico-mecanico/tipo-servico/${tipoServicoId}`, {
            headers: { Authorization: `Bearer ${token}` }
        }).then(response => {
            const orcamentos = response?.data || [];
            if (orcamentos.length === 0) {
                alert("Nenhum orçamento disponível para esse serviço no momento.");
                return;
            }

            sessionStorage.setItem('orcamentosSessao', JSON.stringify(orcamentos));
            carregarOrcamentosSalvos();
        }).catch(err => {
            console.error("Erro ao buscar orçamentos:", err);
            alert("Erro ao buscar orçamentos.");
        });
    });

    function carregarOrcamentosSalvos() {
        const container = document.getElementById('lista-orcamentos');
        const orcamentos = JSON.parse(sessionStorage.getItem('orcamentosSessao')) || [];

        container.innerHTML = '';

        orcamentos.forEach(orcamento => {
            const item = document.createElement('li');
            item.innerHTML = `
                <strong>Tipo:</strong> ${orcamento.tipoOferta} <br>
                <strong>Preço:</strong> R$ ${orcamento.preco.toFixed(2)} <br>
                <strong>Garantia:</strong> ${orcamento.garantiaMeses || '0'} meses <br>
                <strong>Peça:</strong> ${orcamento.nomePeca || 'N/A'} - ${orcamento.marcaPeca || ''} <br>
                <strong>Validade:</strong> ${orcamento.dataVencimentoPeca || '---'}
            `;

            item.classList.add('orcamento-item');
            item.addEventListener('click', () => {
                document.querySelectorAll('.orcamento-item').forEach(el => el.classList.remove('selecionada'));
                item.classList.add('selecionada');
                orcamentoSelecionado = orcamento;
            });

            container.appendChild(item);
        });
    }

    function connectWebSocketCliente() {
        const socket = new SockJS('/ws');
        stompClientCliente = Stomp.over(socket);

        stompClientCliente.connect({ Authorization: 'Bearer ' + token }, () => {
            const clienteId = sessionStorage.getItem("clienteId");
            stompClientCliente.subscribe("/topic/orcamentos-cliente/" + clienteId, message => {
                const orcamento = JSON.parse(message.body);
                let orcamentosSessao = JSON.parse(sessionStorage.getItem('orcamentosSessao')) || [];
                orcamentosSessao.push(orcamento);
                sessionStorage.setItem('orcamentosSessao', JSON.stringify(orcamentosSessao));
                carregarOrcamentosSalvos();
            });
        });
    }

    const modal = document.getElementById("modal-agendamento");
    const spanClose = document.querySelector(".close");
    const btnConfirmar = document.getElementById("confirmar-agendamento-btn");

    document.getElementById('confirmar-agendamento-btn').addEventListener('click', () => {
        if (!orcamentoSelecionado) return alert("Selecione um orçamento antes de agendar.");

        const data = document.getElementById("data").value;
        const horario = document.getElementById("horario").value;

        if (!data || !horario) return alert("Informe data e horário!");

        const dataAgendada = new Date(`${data}T${horario}`);

        stompClientCliente.send("/app/selecionada", {}, JSON.stringify(orcamentoSelecionado));

        axios.post('/servico-mecanico-agendado', {
            clienteId: parseInt(sessionStorage.getItem('clienteId')),
            veiculoId: 1,
            tipoServicoId: orcamentoSelecionado.tipoServicoId,
            dataInicio: dataAgendada,
            mecanicosIds: [1],
            pecasIds: [orcamentoSelecionado.pecaId].filter(Boolean)
        }, {
            headers: { Authorization: `Bearer ${token}` }
        }).then(() => {
            alert("Serviço agendado com sucesso!");
            modal.style.display = "none";

            let orcamentos = JSON.parse(sessionStorage.getItem('orcamentosSessao')) || [];
            orcamentos = orcamentos.filter(o => o.id !== orcamentoSelecionado.id);
            sessionStorage.setItem('orcamentosSessao', JSON.stringify(orcamentos));

            carregarOrcamentosSalvos();
            orcamentoSelecionado = null;
        }).catch(err => {
            console.error("Erro ao agendar o serviço:", err);
            alert("Erro ao agendar o serviço.");
        });
    });

    spanClose.onclick = () => modal.style.display = "none";
    window.onclick = event => {
        if (event.target == modal) modal.style.display = "none";
    };

    connectWebSocketCliente();
    carregarOrcamentosSalvos();
});

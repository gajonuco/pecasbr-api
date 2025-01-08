document.addEventListener("DOMContentLoaded", function () {
    const orcamentoForm = document.getElementById("orcamento-form");
    const orcamentosList = document.getElementById("orcamentos-list");

    orcamentoForm.onsubmit = function (event) {
        event.preventDefault();

        const clienteId = document.getElementById("clienteId").value;
        const servicos = document.getElementById("servicos").value.split(",").map(item => {
            const [servicoId, quantidade] = item.split("-");
            return { servicoId: parseInt(servicoId), quantidade: parseInt(quantidade) };
        });

        const pecas = document.getElementById("pecas").value.split(",").map(item => {
            const [pecaId, quantidade] = item.split("-");
            return { pecaId: parseInt(pecaId), quantidade: parseInt(quantidade) };
        });

        const orcamento = { clienteId: parseInt(clienteId), servicos, pecas };

        fetch("http://localhost:8080/api/orcamentos", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(orcamento)
        })
        .then(() => {
            orcamentoForm.reset();
            alert("Orçamento cadastrado com sucesso!");
        })
        .catch(error => console.error("Erro ao cadastrar orçamento:", error));
    };
});

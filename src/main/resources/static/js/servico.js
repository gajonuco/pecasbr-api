document.addEventListener("DOMContentLoaded", function () {
    const servicoForm = document.getElementById("servico-form");
    const servicosList = document.getElementById("servicos-list");

    function carregarServicos() {
        fetch("http://localhost:8080/api/servicos")
            .then(response => response.json())
            .then(data => {
                servicosList.innerHTML = "";
                data.forEach(servico => {
                    const listItem = document.createElement("li");
                    listItem.classList.add("list-group-item");
                    listItem.textContent = `${servico.nome} - R$ ${servico.preco}`;
                    servicosList.appendChild(listItem);
                });
            })
            .catch(error => console.error("Erro ao carregar serviços:", error));
    }

    servicoForm.onsubmit = function (event) {
        event.preventDefault();
        const servico = {
            nome: document.getElementById("nome").value,
            preco: parseFloat(document.getElementById("preco").value)
        };

        fetch("http://localhost:8080/api/servicos", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(servico)
        })
        .then(() => {
            servicoForm.reset();
            carregarServicos();
            alert("Serviço cadastrado com sucesso!");
        })
        .catch(error => console.error("Erro ao cadastrar serviço:", error));
    };

    carregarServicos();
});

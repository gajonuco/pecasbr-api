document.addEventListener("DOMContentLoaded", function () {
    const pecaForm = document.getElementById("peca-form");
    const pecasList = document.getElementById("pecas-list");

    function carregarPecas() {
        fetch("http://localhost:8080/api/pecas")
            .then(response => response.json())
            .then(data => {
                pecasList.innerHTML = "";
                data.forEach(peca => {
                    const listItem = document.createElement("li");
                    listItem.classList.add("list-group-item");
                    listItem.textContent = `${peca.nome} - R$ ${peca.preco}`;
                    pecasList.appendChild(listItem);
                });
            })
            .catch(error => console.error("Erro ao carregar peças:", error));
    }

    pecaForm.onsubmit = function (event) {
        event.preventDefault();
        const peca = {
            nome: document.getElementById("nome").value,
            preco: parseFloat(document.getElementById("preco").value)
        };

        fetch("http://localhost:8080/api/pecas", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(peca)
        })
        .then(() => {
            pecaForm.reset();
            carregarPecas();
            alert("Peça cadastrada com sucesso!");
        })
        .catch(error => console.error("Erro ao cadastrar peça:", error));
    };

    carregarPecas();
});

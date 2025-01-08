document.addEventListener("DOMContentLoaded", function () {
    const clienteForm = document.getElementById("cliente-form");
    const clientesList = document.getElementById("clientes-list");

    // Função para exibir clientes
    function carregarClientes() {
        fetch("http://localhost:8080/api/clientes")
            .then(response => response.json())
            .then(data => {
                clientesList.innerHTML = "";
                data.forEach(cliente => {
                    const listItem = document.createElement("li");
                    listItem.classList.add("list-group-item");
                    listItem.textContent = `${cliente.nome} - ${cliente.email}`;
                    clientesList.appendChild(listItem);
                });
            })
            .catch(error => console.error("Erro ao carregar clientes:", error));
    }

    // Evento para salvar cliente
    clienteForm.onsubmit = function (event) {
        event.preventDefault();
        const cliente = {
            nome: document.getElementById("nome").value,
            email: document.getElementById("email").value,
            telefone: document.getElementById("telefone").value
        };

        fetch("http://localhost:8080/api/clientes", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(cliente)
        })
        .then(response => response.json())
        .then(() => {
            clienteForm.reset();
            carregarClientes();
            alert("Cliente cadastrado com sucesso!");
        })
        .catch(error => console.error("Erro ao cadastrar cliente:", error));
    };

    carregarClientes();
});

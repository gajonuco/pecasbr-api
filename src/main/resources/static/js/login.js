document.getElementById('login-form').addEventListener('submit', function (e) {
    e.preventDefault();
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    axios.post('http://localhost:8080/auth/login', {
        login: username,
        password: password
    })
    .then(response => {
        const { token, tipo } = response.data;
        sessionStorage.setItem('token', token);
        sessionStorage.setItem('tipo', tipo);

        // Após login, buscar o ID do cliente
        if (tipo === 'CLIENTE') {
            axios.get('http://localhost:8080/api/cliente/me', {
                headers: { Authorization: `Bearer ${token}` }
            })
            .then(cliente => {
                sessionStorage.setItem('clienteId', cliente.data.id); // Correção aplicada aqui
                window.location.href = "/html/painel_cliente.html";
            })
            .catch(() => {
                alert("Erro ao obter dados do cliente.");
            });
        } else if (tipo === 'FUNCIONARIO') {
            window.location.href = "/html/painel_atendete.html";
        } else {
            alert("Tipo de usuário desconhecido.");
        }
    })
    .catch(error => {
        alert("Falha na autenticação.");
        console.error(error);
    });
});

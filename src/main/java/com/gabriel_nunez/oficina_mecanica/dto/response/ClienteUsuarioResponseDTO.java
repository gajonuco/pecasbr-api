package com.gabriel_nunez.oficina_mecanica.dto.response;

public class ClienteUsuarioResponseDTO {
    private Long id;
    private String login;

    public ClienteUsuarioResponseDTO(Long id, String login) {
        this.id = id;
        this.login = login;
    }

    public Long getId() { return id; }
    public String getLogin() { return login; }
}

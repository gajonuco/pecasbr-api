package com.sistema_restful.oficina_mecanica.controllers.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
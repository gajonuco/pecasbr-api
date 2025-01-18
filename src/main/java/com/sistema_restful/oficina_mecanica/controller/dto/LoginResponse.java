package com.sistema_restful.oficina_mecanica.controller.dto;

public record LoginResponse(String accessToken, Long expiresIn) {
}
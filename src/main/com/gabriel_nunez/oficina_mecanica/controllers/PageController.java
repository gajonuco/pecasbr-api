package com.sistema_restful.oficina_mecanica.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/clientes")
    public String clientes() {
        return "clientes";
    }

    @GetMapping("/servicos")
    public String servicos() {
        return "servicos";
    }

    @GetMapping("/pecas")
    public String pecas() {
        return "pecas";
    }

    @GetMapping("/orcamentos")
    public String orcamentos() {
        return "orcamentos";
    }
}

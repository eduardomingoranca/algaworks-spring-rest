package com.algaworks.algafood.api.cliente;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController {

    @GetMapping("/cliente/v1/pedidos")
    public void getClientes() {

    }

}

package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/pedidos/{pedidoId}")
public class FluxoPedidoController {
    @Autowired
    private FluxoPedidoService fluxoPedido;

    @PutMapping("/confirmacao")
    @ResponseStatus(NO_CONTENT)
    public void confirmar(@PathVariable("pedidoId") Long id) {
        fluxoPedido.confirmar(id);
    }

    @PutMapping("/entrega")
    @ResponseStatus(NO_CONTENT)
    public void entregar(@PathVariable("pedidoId") Long id) {
        fluxoPedido.entregar(id);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(NO_CONTENT)
    public void cancelar(@PathVariable("pedidoId") Long id) {
        fluxoPedido.cancelar(id);
    }

}

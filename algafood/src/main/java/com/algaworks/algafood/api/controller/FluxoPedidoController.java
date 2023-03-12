package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/pedidos/{codigoPedido}")
public class FluxoPedidoController {
    @Autowired
    private FluxoPedidoService fluxoPedido;

    @PutMapping("/confirmacao")
    @ResponseStatus(NO_CONTENT)
    public void confirmar(@PathVariable("codigoPedido") String codigo) {
        fluxoPedido.confirmar(codigo);
    }

    @PutMapping("/entrega")
    @ResponseStatus(NO_CONTENT)
    public void entregar(@PathVariable("codigoPedido") String codigo) {
        fluxoPedido.entregar(codigo);
    }

    @PutMapping("/cancelamento")
    @ResponseStatus(NO_CONTENT)
    public void cancelar(@PathVariable("codigoPedido") String codigo) {
        fluxoPedido.cancelar(codigo);
    }

}

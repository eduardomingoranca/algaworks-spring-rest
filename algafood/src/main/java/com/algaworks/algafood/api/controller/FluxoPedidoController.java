package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.openapi.controller.FluxoPedidoControllerOpenAPI;
import com.algaworks.algafood.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/pedidos/{codigoPedido}")
public class FluxoPedidoController implements FluxoPedidoControllerOpenAPI {
    @Autowired
    private FluxoPedidoService fluxoPedido;

    @Override
    @PutMapping("/confirmacao")
    @ResponseStatus(NO_CONTENT)
    public void confirmar(@PathVariable("codigoPedido") String codigo) {
        fluxoPedido.confirmar(codigo);
    }

    @Override
    @PutMapping("/entrega")
    @ResponseStatus(NO_CONTENT)
    public void entregar(@PathVariable("codigoPedido") String codigo) {
        fluxoPedido.entregar(codigo);
    }

    @Override
    @PutMapping("/cancelamento")
    @ResponseStatus(NO_CONTENT)
    public void cancelar(@PathVariable("codigoPedido") String codigo) {
        fluxoPedido.cancelar(codigo);
    }

}

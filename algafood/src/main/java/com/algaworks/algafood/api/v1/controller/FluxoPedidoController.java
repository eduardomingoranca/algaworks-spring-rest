package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.openapi.controller.FluxoPedidoControllerOpenAPI;
import com.algaworks.algafood.core.security.annotation.CheckSecurity;
import com.algaworks.algafood.domain.service.FluxoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping("/v1/pedidos/{codigoPedido}")
public class FluxoPedidoController implements FluxoPedidoControllerOpenAPI {
    @Autowired
    private FluxoPedidoService fluxoPedido;

    @CheckSecurity.Pedidos.PodeGerenciarPedidos
    @Override
    @PutMapping("/confirmacao")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> confirmar(@PathVariable("codigoPedido") String codigo) {
        fluxoPedido.confirmar(codigo);

        return noContent().build();
    }

    @CheckSecurity.Pedidos.PodeGerenciarPedidos
    @Override
    @PutMapping("/entrega")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> entregar(@PathVariable("codigoPedido") String codigo) {
        fluxoPedido.entregar(codigo);

        return noContent().build();
    }

    @CheckSecurity.Pedidos.PodeGerenciarPedidos
    @Override
    @PutMapping("/cancelamento")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> cancelar(@PathVariable("codigoPedido") String codigo) {
        fluxoPedido.cancelar(codigo);

        return noContent().build();
    }

}

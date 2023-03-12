package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {
    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Transactional
    public void confirmar(Long id) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(id);
        pedido.confirmar();
    }

    @Transactional
    public void entregar(Long id) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(id);
        pedido.entregar();
    }

    @Transactional
    public void cancelar(Long id) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(id);
        pedido.cancelar();
    }

}

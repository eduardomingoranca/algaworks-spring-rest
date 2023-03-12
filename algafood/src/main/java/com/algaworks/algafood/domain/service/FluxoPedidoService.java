package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.algaworks.algafood.domain.enumeration.StatusPedido.*;
import static java.lang.String.format;
import static java.time.OffsetDateTime.now;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Transactional
    public void confirmar(Long id) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(id);

        if (!pedido.getStatus().equals(CRIADO)) {
            throw new NegocioException(format("O status do pedido %d nao pode ser alterado de %s para %s",
                    pedido.getId(), pedido.getStatus().getDescricao(), CONFIRMADO.getDescricao()));
        }

        pedido.setStatus(CONFIRMADO);
        pedido.setDataConfirmacao(now());
    }

    @Transactional
    public void entregar(Long id) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(id);

        if (!pedido.getStatus().equals(CONFIRMADO)) {
            throw new NegocioException(format("O status do pedido %d nao pode ser alterado de %s para %s",
                    pedido.getId(), pedido.getStatus().getDescricao(), ENTREGUE.getDescricao()));
        }

        pedido.setStatus(ENTREGUE);
        pedido.setDataConfirmacao(now());
    }

    @Transactional
    public void cancelar(Long id) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(id);

        if (!pedido.getStatus().equals(CRIADO)) {
            throw new NegocioException(format("O status do pedido %d nao pode ser alterado de %s para %s",
                    pedido.getId(), pedido.getStatus().getDescricao(), CANCELADO.getDescricao()));
        }

        pedido.setStatus(CANCELADO);
        pedido.setDataConfirmacao(now());
    }
}

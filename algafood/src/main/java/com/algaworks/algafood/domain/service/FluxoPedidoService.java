package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.enumeration.StatusPedido;
import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AbstractAggregateRoot;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.algaworks.algafood.domain.enumeration.StatusPedido.*;
import static java.time.OffsetDateTime.now;

@Service
public class FluxoPedidoService extends AbstractAggregateRoot<Pedido> {
    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Transactional
    public void confirmar(String codigo) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigo);
        pedido = confirmaPedido(pedido);

        // salvando o pedido no repositorio para disparar o evento
        // quando o metodo for chamado e for feito o descarregamento
        // para o banco de dados, os eventos que foram registrados para
        // serem disparados serao acionados. Podendo ter um ou mais componentes
        // escutando a chamada desse evento para executar algo
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void entregar(String codigo) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigo);
        entregarPedido(pedido);
    }

    @Transactional
    public void cancelar(String codigo) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigo);
        pedido = cancelarPedido(pedido);

        pedidoRepository.save(pedido);
    }

    private Pedido confirmaPedido(Pedido pedido) {
        enviarStatus(CONFIRMADO, pedido);
        pedido.setDataConfirmacao(now());

        // metodo que registra um evento que deve ser disparado/publicado
        // quando o objeto pedido for salvo no repositorio
        PedidoConfirmadoEvent pedidoConfirmadoEvent = new PedidoConfirmadoEvent(pedido);
        registerEvent(pedidoConfirmadoEvent);

        return pedido;
    }

    private void entregarPedido(Pedido pedido) {
        enviarStatus(ENTREGUE, pedido);
        pedido.setDataEntrega(now());
    }

    private Pedido cancelarPedido(Pedido pedido) {
        enviarStatus(CANCELADO, pedido);
        pedido.setDataCancelamento(now());

        PedidoCanceladoEvent pedidoCanceladoEvent = new PedidoCanceladoEvent(pedido);
        registerEvent(pedidoCanceladoEvent);

        return pedido;
    }

    private void enviarStatus(StatusPedido novoStatus, Pedido pedido) {
        if (pedido.getStatus().naoPodeAlterarPara(novoStatus)) {
            throw new NegocioException(String.format("O status do pedido %s nao pode ser alterado de %s para %s",
                    pedido.getCodigo(), pedido.getStatus().getDescricao(), novoStatus.getDescricao()));
        }

        pedido.setStatus(novoStatus);
    }

}

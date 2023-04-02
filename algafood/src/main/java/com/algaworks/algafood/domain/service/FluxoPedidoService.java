package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.mail.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {
    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private EnvioEmailService envioEmail;

    @Transactional
    public void confirmar(String codigo) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigo);
        pedido.confirmar();

        EnvioEmailService.Mensagem mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(pedido.getRestaurante().getNome() + " - Pedido confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(pedido.getCliente().getEmail())
                .build();

        envioEmail.enviar(mensagem);
    }

    @Transactional
    public void entregar(String codigo) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigo);
        pedido.entregar();
    }

    @Transactional
    public void cancelar(String codigo) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigo);
        pedido.cancelar();
    }

}

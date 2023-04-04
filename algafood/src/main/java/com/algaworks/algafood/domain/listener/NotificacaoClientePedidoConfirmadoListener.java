package com.algaworks.algafood.domain.listener;

import com.algaworks.algafood.domain.event.PedidoConfirmadoEvent;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.mail.EnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

// componente que escuta e executa o evento
@Component
public class NotificacaoClientePedidoConfirmadoListener {
    @Autowired
    private EnvioEmailService envioEmail;

    // quando pedido for confirmado vai lancar/disparar o evento
    // o metodo sera chamado automaticamente com a instancia do evento
    // que foi disparado
    // @EventListener
    @TransactionalEventListener
    public void aoConfirmarPedido(PedidoConfirmadoEvent event) {
        Pedido pedido = event.getPedido();
        String nomeRestaurante = pedido.getRestaurante().getNome();
        String email = pedido.getCliente().getEmail();

        EnvioEmailService.Mensagem mensagem = EnvioEmailService.Mensagem.builder()
                .assunto(nomeRestaurante + " - Pedido confirmado")
                .corpo("pedido-confirmado.html")
                .variavel("pedido", pedido)
                .destinatario(email)
                .build();

        envioEmail.enviar(mensagem);
    }

}

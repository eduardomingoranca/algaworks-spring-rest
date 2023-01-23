package com.algaworks.algafood.listener;

import com.algaworks.algafood.di.notificacao.Notificador;
import com.algaworks.algafood.di.notificacao.TipoDoNotificador;
import com.algaworks.algafood.di.service.ClienteAtivadoEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.algaworks.algafood.di.notificacao.NivelUrgencia.SEM_URGENCIA;

@Component
public class NotificacaoService {

    @TipoDoNotificador(SEM_URGENCIA)
    @Autowired
    private Notificador notificador;

    @EventListener
    public void clienteAtivadoListener(ClienteAtivadoEvent event) {
        notificador.notificar(event.cliente(), "Seu cadastro esta ativo!");
    }

}

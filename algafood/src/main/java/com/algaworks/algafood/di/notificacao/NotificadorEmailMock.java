package com.algaworks.algafood.di.notificacao;

import com.algaworks.algafood.di.modelo.Cliente;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import static com.algaworks.algafood.di.notificacao.NivelUrgencia.SEM_URGENCIA;


@Profile("dev")
@TipoDoNotificador(SEM_URGENCIA)
@Component
public class NotificadorEmailMock implements Notificador {

    public NotificadorEmailMock() {
        System.out.println("NotificadorEmail MOCK");
    }

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        System.out.printf("MOCK: Notificacao seria enviada para %s atraves do email %s: %s\n",
                cliente.getNome(), cliente.getEmail(), mensagem);
    }

}

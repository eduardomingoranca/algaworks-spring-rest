package com.algaworks.algafood.di.service;

import com.algaworks.algafood.di.modelo.Cliente;
import com.algaworks.algafood.di.notificacao.Notificador;
import com.algaworks.algafood.di.notificacao.TipoDoNotificador;
import org.springframework.beans.factory.annotation.Autowired;

import static com.algaworks.algafood.di.notificacao.NivelUrgencia.SEM_URGENCIA;

public class AtivacaoClienteService {

    @TipoDoNotificador(SEM_URGENCIA)
    @Autowired
    private Notificador notificador;

    public void init() {
        System.out.println("INIT " + notificador);
    }

    public void destroy() {
        System.out.println("DESTROY");
    }

    public void ativar(Cliente cliente) {
        cliente.ativar();

        notificador.notificar(cliente, "Seu cadastro no sistema esta ativo!");
    }

}

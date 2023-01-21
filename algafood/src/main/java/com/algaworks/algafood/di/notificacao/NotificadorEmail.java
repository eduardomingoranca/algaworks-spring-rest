package com.algaworks.algafood.di.notificacao;

import com.algaworks.algafood.di.modelo.Cliente;

public class NotificadorEmail implements Notificador {

    private boolean caixaAlta;
    private final String hostServidorSMTP;

    public NotificadorEmail(String hostServidorSMTP) {
        this.hostServidorSMTP = hostServidorSMTP;
        System.out.println("NotificadorEmail");
    }

    @Override
    public void notificar(Cliente cliente, String mensagem) {
        if (this.caixaAlta) mensagem = mensagem.toUpperCase();

        System.out.printf("Notificando %s atraves do email %s usando SMTP %s: %s\n",
                cliente.getNome(), cliente.getEmail(), this.hostServidorSMTP, mensagem);
    }

    public void setCaixaAlta(boolean caixaAlta) {
        this.caixaAlta = caixaAlta;
    }

}

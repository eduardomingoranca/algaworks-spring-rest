package com.algaworks.algafood;

import com.algaworks.algafood.di.notificacao.NotificadorEmail;
import com.algaworks.algafood.di.service.AtivacaoClienteService;
import org.springframework.context.annotation.Bean;

public class AlgaConfig {

    @Bean
    public NotificadorEmail notificadorEmail() {
        NotificadorEmail notificadorEmail = new NotificadorEmail("smtp.algamail.com.br");
        notificadorEmail.setCaixaAlta(false);

        return notificadorEmail;
    }

    @Bean
    public AtivacaoClienteService ativacaoClienteService() {
        return new AtivacaoClienteService(notificadorEmail());
    }

}

package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApplication;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.repository.FormaPagamentoRepository;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import static org.springframework.boot.WebApplicationType.NONE;

public class ExclusaoFormaPagamentoMain {

    public static void main(String[] args) {
        // configurando a aplicacao para nao web
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(NONE)
                .run(args);

        FormaPagamentoRepository formasPagamento = applicationContext.getBean(FormaPagamentoRepository.class);

        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setId(3L);

        formasPagamento.remover(formaPagamento);
    }
}

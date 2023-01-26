package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApplication;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import static org.springframework.boot.WebApplicationType.NONE;

public class ExclusaoCidadeMain {

    public static void main(String[] args) {
        // configurando a aplicacao para nao web
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(NONE)
                .run(args);

        CidadeRepository cidades = applicationContext.getBean(CidadeRepository.class);

        cidades.remover(1L);
    }
}

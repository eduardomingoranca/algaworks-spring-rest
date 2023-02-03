package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApplication;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.springframework.boot.WebApplicationType.NONE;

public class ConsultaPermissaoMain {

    public static void main(String[] args) {
        // configurando a aplicacao para nao web
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(NONE)
                .run(args);

        PermissaoRepository permissoes = applicationContext.getBean(PermissaoRepository.class);

        List<Permissao> todasPermissoes = permissoes.findAll();

        for (Permissao permissao: todasPermissoes) {
            System.out.printf("%s | %s\n", permissao.getNome(), permissao.getDescricao());
        }

    }
}

package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApplication;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import static org.springframework.boot.WebApplicationType.NONE;

public class InclusaoEstadoMain {

    public static void main(String[] args) {
        // configurando a aplicacao para nao web
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(NONE)
                .run(args);

        EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);

        Estado estado = new Estado();
        estado.setNome("Rio Grande do Sul");

        estado = estadoRepository.save(estado);

        System.out.printf("%d | %s\n", estado.getId(), estado.getNome());
    }
}

package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApplication;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.List;

import static org.springframework.boot.WebApplicationType.NONE;

public class ConsultaEstadoMain {

    public static void main(String[] args) {
        // configurando a aplicacao para nao web
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(NONE)
                .run(args);

        EstadoRepository estados = applicationContext.getBean(EstadoRepository.class);
        List<Estado> todosEstados = estados.findAll();

        for (Estado estado: todosEstados)
            System.out.printf("%s\n", estado.getNome());
    }
}

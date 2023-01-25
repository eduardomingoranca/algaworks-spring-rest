package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApplication;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
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
        EstadoRepository estados = applicationContext.getBean(EstadoRepository.class);

        Cidade cidade = new Cidade();
        cidade.setId(1L);
        Estado estado = estados.porID(1L);
        cidade.setEstado(estado);

        cidades.remover(cidade);
    }
}

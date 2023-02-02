package com.algaworks.algafood.jpa;

import com.algaworks.algafood.AlgafoodApplication;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

import static org.springframework.boot.WebApplicationType.NONE;

public class InclusaoCidadeMain {

    public static void main(String[] args) {
        // configurando a aplicacao para nao web
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(NONE)
                .run(args);

        CidadeRepository cidadeRepository = applicationContext.getBean(CidadeRepository.class);
        EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);

        Cidade cidade = new Cidade();
        cidade.setNome("Teodoro Sampaio");
        Optional<Estado> estado = estadoRepository.findById(2L);
        if (estado.isPresent()) cidade.setEstado(estado.get());

        cidade = cidadeRepository.save(cidade);

        System.out.printf("%s | %s\n", cidade.getNome(), cidade.getEstado().getNome());
    }
}

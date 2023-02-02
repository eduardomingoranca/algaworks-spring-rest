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

public class AlteracaoCidadeMain {

    public static void main(String[] args) {
        // configurando a aplicacao para nao web
        ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApplication.class)
                .web(NONE)
                .run(args);

        CidadeRepository cidadeRepository = applicationContext.getBean(CidadeRepository.class);
        EstadoRepository estadoRepository = applicationContext.getBean(EstadoRepository.class);

        Cidade cidade = new Cidade();
        cidade.setId(3L);
        cidade.setNome("Cerquilho");
        Optional<Estado> estado = estadoRepository.findById(2L);

        estado.ifPresent(cidade::setEstado);

        cidadeRepository.save(cidade);
    }
}

package com.algaworks.algafood.api.v2.assembler.model;

import com.algaworks.algafood.api.v2.controller.CidadeControllerVersionTwo;
import com.algaworks.algafood.api.v2.links.AlgaLinksVersionTwo;
import com.algaworks.algafood.api.v2.model.CidadeModelVersionTwo;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CidadeModelAssemblerVersionTwo extends RepresentationModelAssemblerSupport<Cidade, CidadeModelVersionTwo> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinksVersionTwo algaLinksVersionTwo;

    public CidadeModelAssemblerVersionTwo() {
            super(CidadeControllerVersionTwo.class, CidadeModelVersionTwo.class);
    }

    @Override
    public CidadeModelVersionTwo toModel(Cidade cidade) {
        CidadeModelVersionTwo cidadeModelVersionTwo = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, cidadeModelVersionTwo);

        // criando um proxy para interceptar uma chamada.
        // gerando a URL dinamicamente
        cidadeModelVersionTwo.add(algaLinksVersionTwo.linkToCidades("cidades"));

        return cidadeModelVersionTwo;
    }

    @Override
    public CollectionModel<CidadeModelVersionTwo> toCollectionModel(Iterable<? extends Cidade> entities) {
        return super.toCollectionModel(entities)
                .add(algaLinksVersionTwo.linkToCidades());
    }

}

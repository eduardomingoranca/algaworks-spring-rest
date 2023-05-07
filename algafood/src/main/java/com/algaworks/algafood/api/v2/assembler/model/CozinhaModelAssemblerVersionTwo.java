package com.algaworks.algafood.api.v2.assembler.model;

import com.algaworks.algafood.api.v2.controller.CozinhaControllerVersionTwo;
import com.algaworks.algafood.api.v2.links.AlgaLinksVersionTwo;
import com.algaworks.algafood.api.v2.model.CozinhaModelVersionTwo;
import com.algaworks.algafood.domain.model.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class CozinhaModelAssemblerVersionTwo extends RepresentationModelAssemblerSupport<Cozinha, CozinhaModelVersionTwo> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinksVersionTwo algaLinksVersionTwo;

    public CozinhaModelAssemblerVersionTwo() {
        super(CozinhaControllerVersionTwo.class, CozinhaModelVersionTwo.class);
    }

    @Override
    public CozinhaModelVersionTwo toModel(Cozinha cozinha) {
        CozinhaModelVersionTwo cozinhaModelVersionTwo = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, cozinhaModelVersionTwo);

        cozinhaModelVersionTwo.add(algaLinksVersionTwo.linkToCozinhas("cozinhas"));

        return cozinhaModelVersionTwo;
    }

}

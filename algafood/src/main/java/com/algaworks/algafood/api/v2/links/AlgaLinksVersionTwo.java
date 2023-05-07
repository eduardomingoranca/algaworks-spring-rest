package com.algaworks.algafood.api.v2.links;

import com.algaworks.algafood.api.v2.controller.CidadeControllerVersionTwo;
import com.algaworks.algafood.api.v2.controller.CozinhaControllerVersionTwo;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.IanaLinkRelations.SELF;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class AlgaLinksVersionTwo {

    public Link linkToCidades(String rel) {
        return linkTo(CidadeControllerVersionTwo.class).withRel(rel);
    }

    public Link linkToCidades() {
        return linkToCidades(SELF.value());
    }

    public Link linkToCozinhas(String rel) {
        return linkTo(CozinhaControllerVersionTwo.class).withRel(rel);
    }

    public Link linkToCozinhas() {
        return linkToCozinhas(SELF.value());
    }

}

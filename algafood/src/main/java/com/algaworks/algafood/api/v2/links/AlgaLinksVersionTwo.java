package com.algaworks.algafood.api.v2.links;

import com.algaworks.algafood.api.v2.controller.CidadeControllerVersionTwo;
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

}

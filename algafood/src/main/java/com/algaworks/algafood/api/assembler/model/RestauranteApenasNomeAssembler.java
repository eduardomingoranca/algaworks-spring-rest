package com.algaworks.algafood.api.assembler.model;

import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.links.AlgaLinks;
import com.algaworks.algafood.api.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class RestauranteApenasNomeAssembler extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeModel> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    public RestauranteApenasNomeAssembler() {
        super(RestauranteController.class, RestauranteApenasNomeModel.class);
    }

    @Override
    public RestauranteApenasNomeModel toModel(Restaurante restaurante) {
        RestauranteApenasNomeModel restauranteApenasNomeModel = createModelWithId(restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteApenasNomeModel);

        restauranteApenasNomeModel.add(algaLinks.linkToRestaurantes("restaurantes"));

        return restauranteApenasNomeModel;
    }

    @Override
    public CollectionModel<RestauranteApenasNomeModel> toCollectionModel(Iterable<? extends Restaurante> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(RestauranteController.class)
                        .withSelfRel());
    }

}

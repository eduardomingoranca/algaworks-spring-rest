package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.model.UsuarioModelAssembler;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.UsuarioModel;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteUsuarioResponsavelControllerOpenAPI;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/v1/restaurantes/{restauranteID}/responsaveis", produces = APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenAPI {
    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private UsuarioModelAssembler usuarioModelAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    @GetMapping
    public CollectionModel<UsuarioModel> listar(@PathVariable("restauranteID") Long id) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);

        CollectionModel<UsuarioModel> usuariosModel = usuarioModelAssembler
                .toCollectionModel(restaurante.getUsuarios())
                .removeLinks()
                .add(algaLinks.linkToResponsaveisRestaurante(id))
                .add(algaLinks.linkToRestauranteUsuarioResponsavelAssociacao(id, "associar"));

        usuariosModel.getContent().forEach(usuarioModel ->
                usuarioModel.add(algaLinks.linkToRestauranteUsuarioResponsavelDesassociar(id, usuarioModel.getId(),
                        "desassociar")));

        return usuariosModel;
    }

    @Override
    @PutMapping("/{usuarioID}")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable("restauranteID") Long id,
                                         @PathVariable Long usuarioID) {
        cadastroRestaurante.associarUsuarioResponsavel(id, usuarioID);

        return noContent().build();
    }

    @Override
    @DeleteMapping("/{usuarioID}")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable("restauranteID") Long id,
                                            @PathVariable Long usuarioID) {
        cadastroRestaurante.desassociarUsuarioResponsavel(id, usuarioID);

        return noContent().build();
    }

}

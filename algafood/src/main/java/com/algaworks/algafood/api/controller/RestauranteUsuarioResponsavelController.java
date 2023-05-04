package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.model.UsuarioModelAssembler;
import com.algaworks.algafood.api.links.AlgaLinks;
import com.algaworks.algafood.api.model.UsuarioModel;
import com.algaworks.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenAPI;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteID}/responsaveis", produces = APPLICATION_JSON_VALUE)
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

        return usuarioModelAssembler.toCollectionModel(restaurante.getUsuarios())
                .removeLinks()
                .add(algaLinks.linkToResponsaveisRestaurante(id));
    }

    @Override
    @PutMapping("/{usuarioID}")
    @ResponseStatus(NO_CONTENT)
    public void associar(@PathVariable("restauranteID") Long id,
                         @PathVariable Long usuarioID) {
        cadastroRestaurante.associarUsuarioResponsavel(id, usuarioID);
    }

    @Override
    @DeleteMapping("/{usuarioID}")
    @ResponseStatus(NO_CONTENT)
    public void desassociar(@PathVariable("restauranteID") Long id,
                            @PathVariable Long usuarioID) {
        cadastroRestaurante.desassociarUsuarioResponsavel(id, usuarioID);
    }

}

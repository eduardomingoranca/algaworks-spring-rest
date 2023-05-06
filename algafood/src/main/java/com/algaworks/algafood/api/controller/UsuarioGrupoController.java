package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.model.GrupoModelAssembler;
import com.algaworks.algafood.api.links.AlgaLinks;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.openapi.controller.UsuarioGrupoControllerOpenAPI;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/usuarios/{usuarioID}/grupos", produces = APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenAPI {
    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    @GetMapping
    public CollectionModel<GrupoModel> listar(@PathVariable("usuarioID") Long id) {
        Usuario usuario = cadastroUsuario.buscarOuFalhar(id);

        CollectionModel<GrupoModel> gruposModel = grupoModelAssembler.toCollectionModel(usuario.getGrupos())
                .removeLinks()
                .add(algaLinks.linkToUsuarioGrupoAssociacao(id, "associar"));

        gruposModel.getContent().forEach(grupoModel ->
                grupoModel.add(algaLinks.linkToUsuarioGrupoDesassociacao(id, grupoModel.getId(),
                        "desassociar")));

        return gruposModel;
    }

    @Override
    @PutMapping("/{grupoID}")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable("usuarioID") Long id,
                                         @PathVariable Long grupoID) {
        cadastroUsuario.associarGrupo(id, grupoID);

        return noContent().build();
    }

    @Override
    @DeleteMapping("/{grupoID}")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable("usuarioID") Long id,
                                            @PathVariable Long grupoID) {
        cadastroUsuario.desassociarGrupo(id, grupoID);

        return noContent().build();
    }

}

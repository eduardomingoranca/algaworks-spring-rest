package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.model.PermissaoModelAssembler;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import com.algaworks.algafood.api.v1.openapi.controller.GrupoPermissaoControllerOpenAPI;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.annotation.CheckSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/v1/grupos/{grupoID}/permissoes", produces = APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenAPI {
    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<PermissaoModel> listar(@PathVariable("grupoID") Long id) {
        Grupo grupo = cadastroGrupo.buscarOuFalhar(id);

        CollectionModel<PermissaoModel> permissoesModel = permissaoModelAssembler.toCollectionModel(grupo.getPermissoes())
                .removeLinks()
                .add(algaLinks.linkToPermissoes("permissoes"));

        if (algaSecurity.podeEditarUsuariosGruposPermissoes()) {
            permissoesModel.add(algaLinks.linkToGruposPermissaoAssociacao(id, "associar"));

            permissoesModel.getContent().forEach(permissaoModel ->
                    permissaoModel.add(algaLinks.linkToGruposPermissaoDesassociar(id, permissaoModel.getId(),
                            "desassociar")));
        }

        return permissoesModel;
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @PutMapping("/{permissaoID}")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable("grupoID") Long id,
                                         @PathVariable Long permissaoID) {
        cadastroGrupo.associarPermissao(id, permissaoID);

        return noContent().build();
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @DeleteMapping("/{permissaoID}")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable("grupoID") Long id,
                                            @PathVariable Long permissaoID) {
        cadastroGrupo.desassociarPermissao(id, permissaoID);

        return noContent().build();
    }

}

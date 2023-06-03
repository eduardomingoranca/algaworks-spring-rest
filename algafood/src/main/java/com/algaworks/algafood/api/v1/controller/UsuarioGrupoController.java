package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.model.GrupoModelAssembler;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.openapi.controller.UsuarioGrupoControllerOpenAPI;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.annotation.CheckSecurity;
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
@RequestMapping(value = "/v1/usuarios/{usuarioID}/grupos", produces = APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenAPI {
    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<GrupoModel> listar(@PathVariable("usuarioID") Long id) {
        Usuario usuario = cadastroUsuario.buscarOuFalhar(id);

        CollectionModel<GrupoModel> gruposModel = grupoModelAssembler.toCollectionModel(usuario.getGrupos())
                .removeLinks();

        if (algaSecurity.podeEditarUsuariosGruposPermissoes()) {
            gruposModel.add(algaLinks.linkToUsuarioGrupoAssociacao(id, "associar"));

            gruposModel.getContent().forEach(grupoModel ->
                    grupoModel.add(algaLinks.linkToUsuarioGrupoDesassociacao(id, grupoModel.getId(),
                            "desassociar")));
        }

        return gruposModel;
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @PutMapping("/{grupoID}")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable("usuarioID") Long id,
                                         @PathVariable Long grupoID) {
        cadastroUsuario.associarGrupo(id, grupoID);

        return noContent().build();
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
    @DeleteMapping("/{grupoID}")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable("usuarioID") Long id,
                                            @PathVariable Long grupoID) {
        cadastroUsuario.desassociarGrupo(id, grupoID);

        return noContent().build();
    }

}

package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.model.GrupoModelAssembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.openapi.controller.UsuarioGrupoControllerOpenAPI;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.CadastroUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/usuarios/{usuarioID}/grupos", produces = APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenAPI {
    @Autowired
    private CadastroUsuarioService cadastroUsuario;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @Override
    @GetMapping
    public CollectionModel<GrupoModel> listar(@PathVariable("usuarioID") Long id) {
        Usuario usuario = cadastroUsuario.buscarOuFalhar(id);

        return grupoModelAssembler.toCollectionModel(usuario.getGrupos());
    }

    @Override
    @PutMapping("/{grupoID}")
    @ResponseStatus(NO_CONTENT)
    public void associar(@PathVariable("usuarioID") Long id,
                         @PathVariable Long grupoID) {
        cadastroUsuario.associarGrupo(id, grupoID);
    }

    @Override
    @DeleteMapping("/{grupoID}")
    @ResponseStatus(NO_CONTENT)
    public void desassociar(@PathVariable("usuarioID") Long id,
                         @PathVariable Long grupoID) {
        cadastroUsuario.desassociarGrupo(id, grupoID);
    }

}

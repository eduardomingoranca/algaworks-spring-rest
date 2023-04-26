package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.model.PermissaoModelAssembler;
import com.algaworks.algafood.api.model.PermissaoModel;
import com.algaworks.algafood.api.openapi.controller.GrupoPermissaoControllerOpenAPI;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/grupos/{grupoID}/permissoes", produces = APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenAPI {
    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private PermissaoModelAssembler permissaoModelAssembler;

    @Override
    @GetMapping
    public List<PermissaoModel> listar(@PathVariable("grupoID") Long id) {
        Grupo grupo = cadastroGrupo.buscarOuFalhar(id);

        return permissaoModelAssembler.toCollectionModel(grupo.getPermissoes());
    }

    @Override
    @PutMapping("/{permissaoID}")
    @ResponseStatus(NO_CONTENT)
    public void associar(@PathVariable("grupoID") Long id,
                         @PathVariable Long permissaoID) {
        cadastroGrupo.associarPermissao(id, permissaoID);
    }

    @Override
    @DeleteMapping("/{permissaoID}")
    @ResponseStatus(NO_CONTENT)
    public void desassociar(@PathVariable("grupoID") Long id,
                         @PathVariable Long permissaoID) {
        cadastroGrupo.desassociarPermissao(id, permissaoID);
    }

}

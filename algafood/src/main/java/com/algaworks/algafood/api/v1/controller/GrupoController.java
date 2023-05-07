package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.input.disassembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.model.GrupoModelAssembler;
import com.algaworks.algafood.api.v1.openapi.controller.GrupoControllerOpenAPI;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/grupos", produces = APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenAPI {
    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private GrupoInputDisassembler grupoInputDisassembler;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @Override
    @GetMapping
    public CollectionModel<GrupoModel> listar() {
        List<Grupo> grupos = cadastroGrupo.listar();

        return grupoModelAssembler.toCollectionModel(grupos);
    }

    @Override
    @GetMapping("/{grupoID}")
    public GrupoModel buscar(@PathVariable("grupoID") Long id) {
        Grupo grupo = cadastroGrupo.buscarOuFalhar(id);

        return grupoModelAssembler.toModel(grupo);
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);
        Grupo salvarGrupo = cadastroGrupo.salvar(grupo);

        return grupoModelAssembler.toModel(salvarGrupo);
    }

    @Override
    @PutMapping("/{grupoID}")
    public GrupoModel atualizar(@PathVariable("grupoID") Long id,
                                @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(id);

        grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);

        Grupo salvarGrupo = cadastroGrupo.salvar(grupoAtual);

        return grupoModelAssembler.toModel(salvarGrupo);
    }

}

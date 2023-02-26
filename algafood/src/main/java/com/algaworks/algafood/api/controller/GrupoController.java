package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.input.disassembler.GrupoInputDisassembler;
import com.algaworks.algafood.api.assembler.model.GrupoModelAssembler;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.CadastroGrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/grupos")
public class GrupoController {
    @Autowired
    private CadastroGrupoService cadastroGrupo;

    @Autowired
    private GrupoInputDisassembler grupoInputDisassembler;

    @Autowired
    private GrupoModelAssembler grupoModelAssembler;

    @GetMapping
    public List<GrupoModel> listar() {
        List<Grupo> grupos = cadastroGrupo.listar();

        return grupoModelAssembler.toCollectionModel(grupos);
    }

    @GetMapping("/{grupoID}")
    public GrupoModel buscar(@PathVariable("grupoID") Long id) {
        Grupo grupo = cadastroGrupo.buscarOuFalhar(id);

        return grupoModelAssembler.toModel(grupo);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public GrupoModel adicionar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);
        Grupo salvarGrupo = cadastroGrupo.salvar(grupo);

        return grupoModelAssembler.toModel(salvarGrupo);
    }

    @PutMapping("/{grupoID}")
    public GrupoModel atualizar(@PathVariable("grupoID") Long id,
                                @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoAtual = cadastroGrupo.buscarOuFalhar(id);

        grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);

        Grupo salvarGrupo = cadastroGrupo.salvar(grupoAtual);

        return grupoModelAssembler.toModel(salvarGrupo);
    }

}

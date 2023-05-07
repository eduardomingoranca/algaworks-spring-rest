package com.algaworks.algafood.api.v2.controller;

import com.algaworks.algafood.api.v2.assembler.input.disassembler.CozinhaInputDisassemblerVersionTwo;
import com.algaworks.algafood.api.v2.assembler.model.CozinhaModelAssemblerVersionTwo;
import com.algaworks.algafood.api.v2.model.CozinhaModelVersionTwo;
import com.algaworks.algafood.api.v2.model.input.CozinhaInputVersionTwo;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/v2/cozinhas", produces = APPLICATION_JSON_VALUE)
public class CozinhaControllerVersionTwo {
    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CozinhaInputDisassemblerVersionTwo cozinhaInputDisassemblerVersionTwo;

    @Autowired
    private CozinhaModelAssemblerVersionTwo cozinhaModelAssemblerVersionTwo;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @ResponseStatus(OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public PagedModel<CozinhaModelVersionTwo> listar(@PageableDefault(size = 2) Pageable pageable) {
        Page<Cozinha> cozinhasPage = cadastroCozinha.listar(pageable);

        // convertendo o page de cozinha para um paged model de cozinha model.
        return pagedResourcesAssembler.toModel(cozinhasPage, cozinhaModelAssemblerVersionTwo);
    }


    @GetMapping("/{cozinhaId}")
    public CozinhaModelVersionTwo buscar(@PathVariable("cozinhaId") Long id) {
        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(id);

        return cozinhaModelAssemblerVersionTwo.toModel(cozinha);
    }


    @PostMapping
    @ResponseStatus(CREATED)
    public CozinhaModelVersionTwo adicionar(@RequestBody @Valid CozinhaInputVersionTwo cozinhaInputVersionTwo) {
        Cozinha cozinha = cozinhaInputDisassemblerVersionTwo.toDomainObject(cozinhaInputVersionTwo);
        Cozinha salvarCozinha = cadastroCozinha.salvar(cozinha);

        return cozinhaModelAssemblerVersionTwo.toModel(salvarCozinha);
    }


    @PutMapping("/{cozinhaId}")
    public CozinhaModelVersionTwo atualizar(@PathVariable("cozinhaId") Long id,
                                  @RequestBody @Valid CozinhaInputVersionTwo cozinhaInputVersionTwo) {
        Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(id);
        cozinhaInputDisassemblerVersionTwo.copyToDomainObject(cozinhaInputVersionTwo, cozinhaAtual);

        Cozinha salvarCozinha = cadastroCozinha.salvar(cozinhaAtual);

        return cozinhaModelAssemblerVersionTwo.toModel(salvarCozinha);
    }


    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(NO_CONTENT)
    public void remover(@PathVariable("cozinhaId") Long id) {
        cadastroCozinha.excluir(id);
    }

}

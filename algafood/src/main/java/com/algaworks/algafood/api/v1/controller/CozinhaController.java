package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.input.disassembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.model.CozinhaModelAssembler;
import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.api.v1.model.input.CozinhaInput;
import com.algaworks.algafood.api.v1.openapi.controller.CozinhaControllerOpenAPI;
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
@RequestMapping(value = "/v1/cozinhas", produces = APPLICATION_JSON_VALUE)
public class CozinhaController implements CozinhaControllerOpenAPI {
    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Autowired
    private CozinhaInputDisassembler cozinhaInputDisassembler;

    @Autowired
    private CozinhaModelAssembler cozinhaModelAssembler;

    @Autowired
    private PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @Override
    @ResponseStatus(OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public PagedModel<CozinhaModel> listar(@PageableDefault(size = 2) Pageable pageable) {
        Page<Cozinha> cozinhasPage = cadastroCozinha.listar(pageable);

        // convertendo o page de cozinha para um paged model de cozinha model.
        return pagedResourcesAssembler.toModel(cozinhasPage, cozinhaModelAssembler);
    }

    @Override
    @GetMapping("/{cozinhaId}")
    public CozinhaModel buscar(@PathVariable("cozinhaId") Long id) {
        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(id);

        return cozinhaModelAssembler.toModel(cozinha);
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
        Cozinha salvarCozinha = cadastroCozinha.salvar(cozinha);

        return cozinhaModelAssembler.toModel(salvarCozinha);
    }

    @Override
    @PutMapping("/{cozinhaId}")
    public CozinhaModel atualizar(@PathVariable("cozinhaId") Long id,
                                  @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(id);
        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

        Cozinha salvarCozinha = cadastroCozinha.salvar(cozinhaAtual);

        return cozinhaModelAssembler.toModel(salvarCozinha);
    }

    @Override
    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(NO_CONTENT)
    public void remover(@PathVariable("cozinhaId") Long id) {
        cadastroCozinha.excluir(id);
    }

}

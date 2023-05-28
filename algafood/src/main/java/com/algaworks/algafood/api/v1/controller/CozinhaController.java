package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.input.disassembler.CozinhaInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.model.CozinhaModelAssembler;
import com.algaworks.algafood.api.v1.model.CozinhaModel;
import com.algaworks.algafood.api.v1.model.input.CozinhaInput;
import com.algaworks.algafood.api.v1.openapi.controller.CozinhaControllerOpenAPI;
import com.algaworks.algafood.core.security.annotation.CheckSecurity;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import lombok.extern.slf4j.Slf4j;
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

// SLF4J => eh uma biblioteca que abstrai o codigos de varios frameworks
// de logging essa biliboteca funciona como uma fachada, repassando as
// operacoes para o framework de logging.
@Slf4j
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

    @CheckSecurity.Cozinhas.PodeConsultar
    @Override
    @ResponseStatus(OK)
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public PagedModel<CozinhaModel> listar(@PageableDefault(size = 2) Pageable pageable) {
        log.info("Consultando cozinhas com paginas de {} registros...", pageable.getPageSize());

        Page<Cozinha> cozinhasPage = cadastroCozinha.listar(pageable);

        // convertendo o page de cozinha para um paged model de cozinha model.
        return pagedResourcesAssembler.toModel(cozinhasPage, cozinhaModelAssembler);
    }

    @CheckSecurity.Cozinhas.PodeConsultar
    @Override
    @GetMapping("/{cozinhaId}")
    public CozinhaModel buscar(@PathVariable("cozinhaId") Long id) {
        Cozinha cozinha = cadastroCozinha.buscarOuFalhar(id);

        return cozinhaModelAssembler.toModel(cozinha);
    }

    @CheckSecurity.Cozinhas.PodeEditar
    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public CozinhaModel adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
        Cozinha salvarCozinha = cadastroCozinha.salvar(cozinha);

        return cozinhaModelAssembler.toModel(salvarCozinha);
    }

    @CheckSecurity.Cozinhas.PodeEditar
    @Override
    @PutMapping("/{cozinhaId}")
    public CozinhaModel atualizar(@PathVariable("cozinhaId") Long id,
                                  @RequestBody @Valid CozinhaInput cozinhaInput) {
        Cozinha cozinhaAtual = cadastroCozinha.buscarOuFalhar(id);
        cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaAtual);

        Cozinha salvarCozinha = cadastroCozinha.salvar(cozinhaAtual);

        return cozinhaModelAssembler.toModel(salvarCozinha);
    }

    @CheckSecurity.Cozinhas.PodeEditar
    @Override
    @DeleteMapping("/{cozinhaId}")
    @ResponseStatus(NO_CONTENT)
    public void remover(@PathVariable("cozinhaId") Long id) {
        cadastroCozinha.excluir(id);
    }

}

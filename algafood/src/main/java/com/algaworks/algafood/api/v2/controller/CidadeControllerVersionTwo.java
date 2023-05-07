package com.algaworks.algafood.api.v2.controller;

import com.algaworks.algafood.api.v2.assembler.input.disassembler.CidadeInputDisassemblerVersionTwo;
import com.algaworks.algafood.api.v2.assembler.model.CidadeModelAssemblerVersionTwo;
import com.algaworks.algafood.api.v2.model.CidadeModelVersionTwo;
import com.algaworks.algafood.api.v2.model.input.CidadeInputVersionTwo;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.algaworks.algafood.api.utils.ResourceURIHelper.addURIInResponseHeader;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/v2/cidades", produces = APPLICATION_JSON_VALUE)
public class CidadeControllerVersionTwo {
    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeInputDisassemblerVersionTwo cidadeInputDisassemblerVersionTwo;

    @Autowired
    private CidadeModelAssemblerVersionTwo cidadeModelAssemblerVersionTwo;


    @GetMapping
    public CollectionModel<CidadeModelVersionTwo> listar() {
        List<Cidade> cidades = cadastroCidade.listar();

        return cidadeModelAssemblerVersionTwo.toCollectionModel(cidades);
    }


    @GetMapping(value = "/{cidadeId}")
    public CidadeModelVersionTwo buscar(@PathVariable("cidadeId") Long id) {
        Cidade cidade = cadastroCidade.buscarOuFalhar(id);

        return cidadeModelAssemblerVersionTwo.toModel(cidade);
    }


    @PostMapping
    @ResponseStatus(CREATED)
    public CidadeModelVersionTwo adicionar(@RequestBody @Valid CidadeInputVersionTwo cidadeInputVersionTwo) {
        try {
            Cidade cidade = cidadeInputDisassemblerVersionTwo.toDomainObject(cidadeInputVersionTwo);
            Cidade salvarCidade = cadastroCidade.salvar(cidade);

            CidadeModelVersionTwo cidadeModelVersionTwo = cidadeModelAssemblerVersionTwo.toModel(salvarCidade);
            addURIInResponseHeader(cidadeModelVersionTwo.getIdCidade());

            return cidadeModelVersionTwo;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }


    @PutMapping(value = "/{cidadeId}")
    public CidadeModelVersionTwo atualizar(@PathVariable("cidadeId") Long id,
                                           @RequestBody @Valid CidadeInputVersionTwo cidadeInputVersionTwo) {
        try {
            Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);
            cidadeInputDisassemblerVersionTwo.copyToDomainObject(cidadeInputVersionTwo, cidadeAtual);

            Cidade salvarCidade = cadastroCidade.salvar(cidadeAtual);

            return cidadeModelAssemblerVersionTwo.toModel(salvarCidade);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

}

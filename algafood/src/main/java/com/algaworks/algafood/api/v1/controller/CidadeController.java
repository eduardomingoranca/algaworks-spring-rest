package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.input.disassembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.model.CidadeModelAssembler;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;
import com.algaworks.algafood.api.v1.openapi.controller.CidadeControllerOpenAPI;
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
import static com.algaworks.algafood.core.web.AlgaMediaTypes.V1_APPLICATION_JSON_VALUE;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController implements CidadeControllerOpenAPI {
    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Override
    @GetMapping(produces = V1_APPLICATION_JSON_VALUE)
    public CollectionModel<CidadeModel> listar() {
        List<Cidade> cidades = cadastroCidade.listar();

        return cidadeModelAssembler.toCollectionModel(cidades);
    }

    @Override
    @GetMapping(value = "/{cidadeId}", produces = V1_APPLICATION_JSON_VALUE)
    public CidadeModel buscar(@PathVariable("cidadeId") Long id) {
        Cidade cidade = cadastroCidade.buscarOuFalhar(id);

        return cidadeModelAssembler.toModel(cidade);
    }

    @Override
    @PostMapping(produces = V1_APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public CidadeModel adicionar(@RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
            Cidade salvarCidade = cadastroCidade.salvar(cidade);

            CidadeModel cidadeModel = cidadeModelAssembler.toModel(salvarCidade);
            addURIInResponseHeader(cidadeModel.getId());

            return cidadeModel;
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @PutMapping(value = "/{cidadeId}", produces = V1_APPLICATION_JSON_VALUE)
    public CidadeModel atualizar(@PathVariable("cidadeId") Long id,
                                 @RequestBody @Valid CidadeInput cidadeInput) {
        try {
            Cidade cidadeAtual = cadastroCidade.buscarOuFalhar(id);
            cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeAtual);

            Cidade salvarCidade = cadastroCidade.salvar(cidadeAtual);

            return cidadeModelAssembler.toModel(salvarCidade);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

}

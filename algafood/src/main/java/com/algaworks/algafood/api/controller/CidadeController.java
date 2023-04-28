package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.input.disassembler.CidadeInputDisassembler;
import com.algaworks.algafood.api.assembler.model.CidadeModelAssembler;
import com.algaworks.algafood.api.model.CidadeModel;
import com.algaworks.algafood.api.model.input.CidadeInput;
import com.algaworks.algafood.api.openapi.controller.CidadeControllerOpenAPI;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CadastroCidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.algaworks.algafood.api.utils.ResourceURIHelper.addURIInResponseHeader;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/cidades", produces = APPLICATION_JSON_VALUE)
public class CidadeController implements CidadeControllerOpenAPI {
    @Autowired
    private CadastroCidadeService cadastroCidade;

    @Autowired
    private CidadeInputDisassembler cidadeInputDisassembler;

    @Autowired
    private CidadeModelAssembler cidadeModelAssembler;

    @Override
    @GetMapping
    public List<CidadeModel> listar() {
        List<Cidade> cidades = cadastroCidade.listar();

        return cidadeModelAssembler.toCollectionModel(cidades);
    }

    @Override
    @GetMapping("/{cidadeId}")
    public CidadeModel buscar(@PathVariable("cidadeId") Long id) {
        Cidade cidade = cadastroCidade.buscarOuFalhar(id);

        CidadeModel cidadeModel = cidadeModelAssembler.toModel(cidade);

        // criando um proxy para interceptar uma chamada.
        // gerando a URL dinamicamente
        Link buscarCidadeURL = linkTo(methodOn(CidadeController.class)
                .buscar(cidadeModel.getId()))
                .withSelfRel();

        cidadeModel.add(buscarCidadeURL);

//        cidadeModel.add(linkTo(CidadeController.class)
//                .slash(cidadeModel.getId()).withSelfRel());

        Link listarCidadeURL = linkTo(methodOn(CidadeController.class)
                .listar())
                .withRel("cidades");

        cidadeModel.add(listarCidadeURL);

//        cidadeModel.add(linkTo(CidadeController.class)
//                .withRel("cidades"));

        Link buscarEstadoURL = linkTo(methodOn(EstadoController.class)
                .buscar(cidadeModel.getEstado().getId()))
                .withSelfRel();

        cidadeModel.getEstado().add(buscarEstadoURL);

//        cidadeModel.getEstado().add(linkTo(EstadoController.class)
//                .slash(cidadeModel.getEstado().getId()).withSelfRel());

        return cidadeModel;
    }

    @Override
    @PostMapping
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
    @PutMapping("/{cidadeId}")
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

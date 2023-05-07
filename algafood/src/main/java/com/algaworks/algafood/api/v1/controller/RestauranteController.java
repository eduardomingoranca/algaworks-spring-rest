package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.input.disassembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.model.RestauranteApenasNomeAssembler;
import com.algaworks.algafood.api.v1.assembler.model.RestauranteBasicoModelAssembler;
import com.algaworks.algafood.api.v1.assembler.model.RestauranteModelAssembler;
import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteControllerOpenAPI;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/v1/restaurantes", produces = APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenAPI {
    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    @Autowired
    private RestauranteBasicoModelAssembler restauranteBasicoModelAssembler;

    @Autowired
    private RestauranteApenasNomeAssembler restauranteApenasNomeAssembler;

    @Override
    @GetMapping
    public CollectionModel<RestauranteBasicoModel> listar() {
        return restauranteBasicoModelAssembler
                .toCollectionModel(cadastroRestaurante.listar());
    }

    @Override
    @GetMapping(params = "projecao=apenas-nome")
    public CollectionModel<RestauranteApenasNomeModel> listarApenasNomes() {
        return restauranteApenasNomeAssembler
                .toCollectionModel(cadastroRestaurante.listar());
    }

    @Override
    @GetMapping("/{restauranteId}")
    public RestauranteModel buscar(@PathVariable("restauranteId") Long id) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);

        return restauranteModelAssembler.toModel(restaurante);
    }

//    @Valid -> anotacao valida a entrada de dados na instancia da classe antes de executar o metodo.
//    @Validated -> quando for cadastrar um restaurante sera validado um objeto do tipo restaurante que possuem
//    constraints do grupo cadastro restaurante.
    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);
            Restaurante salvarRestaurante = cadastroRestaurante.salvar(restaurante);

            return restauranteModelAssembler.toModel(salvarRestaurante);
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@PathVariable("restauranteId") Long id,
                                      @RequestBody @Valid RestauranteInput restauranteInput) {
        try {
            Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);

            restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

            Restaurante salvarRestaurante = cadastroRestaurante.salvar(restauranteAtual);

            return restauranteModelAssembler.toModel(salvarRestaurante);
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    // PUT /restaurantes/{id}/ativo
    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> ativar(@PathVariable("restauranteId") Long id) {
        cadastroRestaurante.ativar(id);

        return noContent().build();
    }

    @Override
    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> inativar(@PathVariable("restauranteId") Long id) {
        cadastroRestaurante.inativar(id);

        return noContent().build();
    }

    @Override
    // PUT /restaurantes/ativacoes
    @PutMapping("/ativacoes")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> ativarMultiplos(@RequestBody List<Long> restauranteIDs) {
        try {
            cadastroRestaurante.ativar(restauranteIDs);

            return noContent().build();
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    // DELETE /restaurantes/ativacoes
    @DeleteMapping("/ativacoes")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> inativarMultiplos(@RequestBody List<Long> restauranteIDs) {
        try {
            cadastroRestaurante.inativar(restauranteIDs);

            return noContent().build();
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> fechar(@PathVariable("restauranteId") Long id) {
        cadastroRestaurante.fechar(id);

        return noContent().build();
    }

    @Override
    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> abrir(@PathVariable("restauranteId") Long id) {
        cadastroRestaurante.abrir(id);

        return noContent().build();
    }

}

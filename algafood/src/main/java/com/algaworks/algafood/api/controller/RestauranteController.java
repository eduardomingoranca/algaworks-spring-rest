package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.input.disassembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.assembler.model.RestauranteModelAssembler;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.openapi.controller.RestauranteControllerOpenAPI;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/restaurantes", produces = APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenAPI {
    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    @Override
    @GetMapping
    public List<RestauranteModel> listar() {
        List<Restaurante> restaurantes = cadastroRestaurante.listar();
        return restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @Override
    @GetMapping(params = "projecao=apenas-nome")
    public List<RestauranteModel> listarApenasNomes() {
        return listar();
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
    public void ativar(@PathVariable("restauranteId") Long id) {
        cadastroRestaurante.ativar(id);
    }

    @Override
    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(NO_CONTENT)
    public void inativar(@PathVariable("restauranteId") Long id) {
        cadastroRestaurante.inativar(id);
    }

    @Override
    // PUT /restaurantes/ativacoes
    @PutMapping("/ativacoes")
    @ResponseStatus(NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restauranteIDs) {
        try {
            cadastroRestaurante.ativar(restauranteIDs);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    // DELETE /restaurantes/ativacoes
    @DeleteMapping("/ativacoes")
    @ResponseStatus(NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restauranteIDs) {
        try {
            cadastroRestaurante.inativar(restauranteIDs);
        } catch (RestauranteNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @Override
    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(NO_CONTENT)
    public void fechar(@PathVariable("restauranteId") Long id) {
        cadastroRestaurante.fechar(id);
    }

    @Override
    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(NO_CONTENT)
    public void abrir(@PathVariable("restauranteId") Long id) {
        cadastroRestaurante.abrir(id);
    }

}

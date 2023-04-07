package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.input.disassembler.RestauranteInputDisassembler;
import com.algaworks.algafood.api.assembler.model.RestauranteModelAssembler;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.model.view.RestauranteView;
import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

//@CrossOrigin(origins = "http://127.0.0.1:8000")
//@CrossOrigin
// maxAge -> define qual o tempo maximo em segundos que o browser pode
// armazenar o cache do preflight
@CrossOrigin(maxAge = 10)
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private RestauranteModelAssembler restauranteModelAssembler;

    @Autowired
    private RestauranteInputDisassembler restauranteInputDisassembler;

    @JsonView(RestauranteView.Resumo.class)
    @GetMapping
    public List<RestauranteModel> listar() {
        List<Restaurante> restaurantes = cadastroRestaurante.listar();
        return restauranteModelAssembler.toCollectionModel(restaurantes);
    }

    @GetMapping("/{restauranteId}")
    public RestauranteModel buscar(@PathVariable("restauranteId") Long id) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);

        return restauranteModelAssembler.toModel(restaurante);
    }

    //    @Valid -> anotacao valida a entrada de dados na instancia da classe antes de executar o metodo.
//    @Validated -> quando for cadastrar um restaurante sera validado um objeto do tipo restaurante que possuem
//    constraints do grupo cadastro restaurante.
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

    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@PathVariable("restauranteId") Long id,
                                      @RequestBody @Valid RestauranteInput restauranteInput) {
        try {
//            Restaurante restaurante = restauranteInputDisassembler.toDomainObject(restauranteInput);

            Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);

            restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteAtual);

//            copyProperties(restaurante, restauranteAtual, "id", "formasPagamento",
//                    "endereco", "dataCadastro", "produtos");

            Restaurante salvarRestaurante = cadastroRestaurante.salvar(restauranteAtual);

            return restauranteModelAssembler.toModel(salvarRestaurante);
        } catch (CozinhaNaoEncontradaException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    // PUT /restaurantes/{id}/ativo
    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(NO_CONTENT)
    public void ativar(@PathVariable("restauranteId") Long id) {
        cadastroRestaurante.ativar(id);
    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(NO_CONTENT)
    public void inativar(@PathVariable("restauranteId") Long id) {
        cadastroRestaurante.inativar(id);
    }

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

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(NO_CONTENT)
    public void fechar(@PathVariable("restauranteId") Long id) {
        cadastroRestaurante.fechar(id);
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(NO_CONTENT)
    public void abrir(@PathVariable("restauranteId") Long id) {
        cadastroRestaurante.abrir(id);
    }

}

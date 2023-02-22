package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.CozinhaModel;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.beans.BeanUtils.copyProperties;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {
    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @GetMapping
    public List<RestauranteModel> listar() {
        List<Restaurante> restaurantes = cadastroRestaurante.listar();

        return toCollectionModel(restaurantes);
    }

    @GetMapping("/{restauranteId}")
    public RestauranteModel buscar(@PathVariable("restauranteId") Long id) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);

        return toModel(restaurante);
    }

    //    @Valid -> anotacao valida a entrada de dados na instancia da classe antes de executar o metodo.
//    @Validated -> quando for cadastrar um restaurante sera validado um objeto do tipo restaurante que possuem
//    constraints do grupo cadastro restaurante.
    @PostMapping
    @ResponseStatus(CREATED)
    public RestauranteModel adicionar(@RequestBody @Valid Restaurante restaurante) {
        try {
            Restaurante salvarRestaurante = cadastroRestaurante.salvar(restaurante);

            return toModel(salvarRestaurante);
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteModel atualizar(@PathVariable("restauranteId") Long id, @RequestBody @Valid Restaurante restaurante) {
        try {
            Restaurante restauranteAtual = cadastroRestaurante.buscarOuFalhar(id);
            copyProperties(restaurante, restauranteAtual, "id", "formasPagamento",
                    "endereco", "dataCadastro", "produtos");

            Restaurante salvarRestaurante = cadastroRestaurante.salvar(restauranteAtual);

            return toModel(salvarRestaurante);
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    private static RestauranteModel toModel(Restaurante restaurante) {
        CozinhaModel cozinhaModel = new CozinhaModel();
        cozinhaModel.setId(restaurante.getCozinha().getId());
        cozinhaModel.setNome(restaurante.getCozinha().getNome());

        // conversao da entidade Restaurante para RestauranteModel
        RestauranteModel restauranteModel = new RestauranteModel();
        restauranteModel.setId(restaurante.getId());
        restauranteModel.setNome(restaurante.getNome());
        restauranteModel.setTaxaFrete(restaurante.getTaxaFrete());
        restauranteModel.setCozinha(cozinhaModel);

        return restauranteModel;
    }

    private List<RestauranteModel> toCollectionModel(List<Restaurante> restaurantes) {
        // convertendo cada restaurante em restaurante model
//        return restaurantes.stream().map(restaurante -> toModel(restaurante)).collect(Collectors.toList());
        return restaurantes.stream().map(RestauranteController::toModel).collect(toList());
    }

}

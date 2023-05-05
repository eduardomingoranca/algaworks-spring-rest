package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.model.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.api.openapi.controller.RestauranteFormaPagamentoControllerOpenAPI;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteID}/formas-pagamento", produces = APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenAPI {
    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @Override
    @GetMapping
    public CollectionModel<FormaPagamentoModel> listar(@PathVariable("restauranteID") Long id) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);

        return formaPagamentoModelAssembler.toCollectionModel(restaurante.getFormasPagamento());
    }

    @Override
    @DeleteMapping("/{formaPagamentoID}")
    @ResponseStatus(NO_CONTENT)
    public void desassociar(@PathVariable("restauranteID") Long id, @PathVariable Long formaPagamentoID) {
        cadastroRestaurante.desassociarFormaPagamento(id, formaPagamentoID);
    }

    @Override
    @PutMapping("/{formaPagamentoID}")
    @ResponseStatus(NO_CONTENT)
    public void associar(@PathVariable("restauranteID") Long id, @PathVariable Long formaPagamentoID) {
        cadastroRestaurante.associarFormaPagamento(id, formaPagamentoID);
    }

}

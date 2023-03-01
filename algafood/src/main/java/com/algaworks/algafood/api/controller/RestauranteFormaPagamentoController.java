package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.model.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.model.FormaPagamentoModel;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/restaurantes/{restauranteID}/formas-pagamento")
public class RestauranteFormaPagamentoController {
    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @GetMapping
    public List<FormaPagamentoModel> listar(@PathVariable("restauranteID") Long id) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);

        return formaPagamentoModelAssembler.toCollectionModel(restaurante.getFormasPagamento());
    }

    @DeleteMapping("/{formaPagamentoID}")
    @ResponseStatus(NO_CONTENT)
    public void desassociar(@PathVariable("restauranteID") Long id, @PathVariable Long formaPagamentoID) {
        cadastroRestaurante.desassociarFormaPagamento(id, formaPagamentoID);
    }

    @PutMapping("/{formaPagamentoID}")
    @ResponseStatus(NO_CONTENT)
    public void associar(@PathVariable("restauranteID") Long id, @PathVariable Long formaPagamentoID) {
        cadastroRestaurante.associarFormaPagamento(id, formaPagamentoID);
    }

}

package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.model.FormaPagamentoModelAssembler;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteFormaPagamentoControllerOpenAPI;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.core.security.annotation.CheckSecurity;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.noContent;

@RestController
@RequestMapping(value = "/v1/restaurantes/{restauranteID}/formas-pagamento", produces = APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenAPI {
    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private FormaPagamentoModelAssembler formaPagamentoModelAssembler;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    @CheckSecurity.Restaurantes.PodeConsultar
    @Override
    @GetMapping
    public CollectionModel<FormaPagamentoModel> listar(@PathVariable("restauranteID") Long id) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);

        CollectionModel<FormaPagamentoModel> formasPagamentoModel = formaPagamentoModelAssembler
                .toCollectionModel(restaurante.getFormasPagamento())
                .removeLinks()
                .add(algaLinks.linkToFormaPagamentoRestaurante(id));

        if (algaSecurity.podeGerenciarFuncionamentoRestaurantes(id)) {
            formasPagamentoModel.add(algaLinks.linkToRestauranteFormaPagamentoAssociacao(id, "associar"));

            formasPagamentoModel.getContent().forEach(formaPagamentoModel ->
                    formaPagamentoModel.add(algaLinks.linkToRestauranteFormaPagamentoDesassociacao(id,
                            formaPagamentoModel.getId(), "desassociar")));
        }

        return formasPagamentoModel;
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
    @DeleteMapping("/{formaPagamentoID}")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> desassociar(@PathVariable("restauranteID") Long id, @PathVariable Long formaPagamentoID) {
        cadastroRestaurante.desassociarFormaPagamento(id, formaPagamentoID);

        return noContent().build();
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @Override
    @PutMapping("/{formaPagamentoID}")
    @ResponseStatus(NO_CONTENT)
    public ResponseEntity<Void> associar(@PathVariable("restauranteID") Long id, @PathVariable Long formaPagamentoID) {
        cadastroRestaurante.associarFormaPagamento(id, formaPagamentoID);

        return noContent().build();
    }

}

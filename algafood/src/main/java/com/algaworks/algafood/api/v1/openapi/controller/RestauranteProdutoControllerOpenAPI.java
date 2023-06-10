package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.input.ProdutoInput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
public interface RestauranteProdutoControllerOpenAPI {

    CollectionModel<ProdutoModel> listar(Long id, Boolean incluirInativos);

    ProdutoModel buscar(Long id, Long produtoID);

    ProdutoModel adicionar(Long id, ProdutoInput produtoInput);

    ProdutoModel atualizar(Long id, Long produtoID, ProdutoInput produtoInput);

}

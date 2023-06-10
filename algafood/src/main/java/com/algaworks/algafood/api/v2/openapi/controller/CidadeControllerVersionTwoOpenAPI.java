package com.algaworks.algafood.api.v2.openapi.controller;

import com.algaworks.algafood.api.v2.model.CidadeModelVersionTwo;
import com.algaworks.algafood.api.v2.model.input.CidadeInputVersionTwo;
import org.springframework.hateoas.CollectionModel;

public interface CidadeControllerVersionTwoOpenAPI {
    CollectionModel<CidadeModelVersionTwo> listar();

    CidadeModelVersionTwo buscar(Long id);

    CidadeModelVersionTwo adicionar(CidadeInputVersionTwo cidadeInputVersionTwo);

    CidadeModelVersionTwo atualizar(Long id, CidadeInputVersionTwo cidadeInputVersionTwo);
}

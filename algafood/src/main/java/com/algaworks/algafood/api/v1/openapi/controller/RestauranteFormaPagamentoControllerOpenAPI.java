package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.FormaPagamentoModel;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
public interface RestauranteFormaPagamentoControllerOpenAPI {

    CollectionModel<FormaPagamentoModel> listar(Long id);

    ResponseEntity<Void> desassociar(Long id, Long formaPagamentoID);

    ResponseEntity<Void> associar(Long id, Long formaPagamentoID);

}

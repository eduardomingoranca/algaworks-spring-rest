package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
public interface EstadoControllerOpenAPI {

    CollectionModel<EstadoModel> listar();

    EstadoModel buscar(Long id);

    EstadoModel adicionar(EstadoInput estadoInput);

    EstadoModel atualizar(Long id, EstadoInput estadoInput);

}

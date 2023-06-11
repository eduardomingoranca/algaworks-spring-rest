package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@SecurityRequirement(name = "security_auth")
public interface RestauranteControllerOpenAPI {

    @Operation(parameters = {
            @Parameter(name = "projecao",
            description = "Nome da projecao",
            example = "apenas-nome",
            in = QUERY)
    })
    CollectionModel<RestauranteBasicoModel> listar();

    @Operation(hidden = true)
    CollectionModel<RestauranteApenasNomeModel> listarApenasNomes();

    RestauranteModel buscar(Long id);

    RestauranteModel adicionar(RestauranteInput restauranteInput);

    RestauranteModel atualizar(Long id, RestauranteInput restauranteInput);

    ResponseEntity<Void> ativar(Long id);

    ResponseEntity<Void> inativar(Long id);

    ResponseEntity<Void> ativarMultiplos(List<Long> restauranteIDs);

    ResponseEntity<Void> inativarMultiplos(List<Long> restauranteIDs);

    ResponseEntity<Void> fechar(Long id);

    ResponseEntity<Void> abrir(Long id);

}


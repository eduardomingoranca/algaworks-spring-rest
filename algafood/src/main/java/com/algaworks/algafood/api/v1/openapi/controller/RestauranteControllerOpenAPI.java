package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = "security_auth")
public interface RestauranteControllerOpenAPI {

    CollectionModel<RestauranteBasicoModel> listar();

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


package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.v1.model.RestauranteApenasNomeModel;
import com.algaworks.algafood.api.v1.model.RestauranteBasicoModel;
import com.algaworks.algafood.api.v1.model.RestauranteModel;
import com.algaworks.algafood.api.v1.model.input.RestauranteInput;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = "security_auth")
public interface RestauranteControllerOpenAPI {

    CollectionModel<RestauranteBasicoModel> listar();

    CollectionModel<RestauranteApenasNomeModel> listarApenasNomes();

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do restaurante invalido", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    RestauranteModel buscar(Long id);

    @ApiResponses(@ApiResponse(responseCode = "201", description = "Restaurante cadastrado"))
    RestauranteModel adicionar(RestauranteInput restauranteInput);

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    RestauranteModel atualizar(Long id, RestauranteInput restauranteInput);

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> ativar(Long id);

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> inativar(Long id);

    @ApiResponses(@ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso"))
    ResponseEntity<Void> ativarMultiplos(List<Long> restauranteIDs);

    @ApiResponses(@ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso"))
    ResponseEntity<Void> inativarMultiplos(List<Long> restauranteIDs);

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> fechar(Long id);

    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> abrir(Long id);

}


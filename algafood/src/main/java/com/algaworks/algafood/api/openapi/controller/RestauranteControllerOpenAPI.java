package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.model.RestauranteModel;
import com.algaworks.algafood.api.model.input.RestauranteInput;
import com.algaworks.algafood.api.model.view.RestauranteView;
import com.algaworks.algafood.api.openapi.model.RestauranteBasicoModelOpenAPI;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenAPI {

    @ApiOperation(value = "Lista de restaurantes", response = RestauranteBasicoModelOpenAPI.class)
    @ApiImplicitParams(@ApiImplicitParam(value = "Nome da projecao de pedidos",
            allowableValues = "apenas-nome", name = "projecao", paramType = "query", dataTypeClass = String.class))
    @JsonView(RestauranteView.Resumo.class)
    List<RestauranteModel> listar();

    @ApiOperation(value = "Lista de restaurantes", hidden = true)
    @JsonView(RestauranteView.ApenasNome.class)
    List<RestauranteModel> listarApenasNomes();

    @ApiOperation("Busca um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do restaurante invalido", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    RestauranteModel buscar(@ApiParam(value = "ID de um restaurante", required = true) Long id);

    @ApiOperation("Cadastra um restaurante")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "Restaurante cadastrado"))
    RestauranteModel adicionar(@ApiParam(name = "corpo", value = "Representacao de um novo restaurante", required = true)
                               RestauranteInput restauranteInput);

    @ApiOperation("Atualiza um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    RestauranteModel atualizar(@ApiParam(value = "ID de um restaurante", required = true) Long id,
                               @ApiParam(name = "corpo", value = "Representacao de um novo restaurante", required = true)
                               RestauranteInput restauranteInput);

    @ApiOperation("Ativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    void ativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Inativa um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    void inativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Ativa multiplos restaurantes")
    @ApiResponses(@ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso"))
    void ativarMultiplos(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
                         List<Long> restauranteIDs);

    @ApiOperation("Inativa multiplos restaurantes")
    @ApiResponses(@ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso"))
    void inativarMultiplos(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true)
                           List<Long> restauranteIDs);

    @ApiOperation("Fecha um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    void fechar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

    @ApiOperation("Abre um restaurante por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    void abrir(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

}


package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.model.GrupoModel;
import com.algaworks.algafood.api.model.input.GrupoInput;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@Api(tags = "Grupos")
public interface GrupoControllerOpenAPI {
    @ApiOperation("Lista os grupos")
    List<GrupoModel> listar();

    @ApiOperation("Busca um grupo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do grupo invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Grupo nao encontrado.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    GrupoModel buscar(@ApiParam(value = "ID de um grupo", required = true) Long id);

    @ApiOperation("Cadastra um grupo")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "Grupo cadastrado."))
    GrupoModel adicionar(@ApiParam(name = "corpo", value = "Representacao de um novo grupo.", required = true)
                         GrupoInput grupoInput);

    @ApiOperation("Atualiza um grupo por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grupo atualizado."),
            @ApiResponse(responseCode = "404", description = "Grupo nao encontrado.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    GrupoModel atualizar(@ApiParam(value = "ID de um grupo", required = true) Long id,
                         @ApiParam(name = "corpo", value = "Representacao de um grupo com os novos dados",
                                 required = true)
                         GrupoInput grupoInput);

}

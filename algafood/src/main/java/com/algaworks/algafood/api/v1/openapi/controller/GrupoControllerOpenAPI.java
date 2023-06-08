package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import com.algaworks.algafood.api.v1.model.input.GrupoInput;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;

public interface GrupoControllerOpenAPI {
    CollectionModel<GrupoModel> listar();

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID do grupo invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Grupo nao encontrado.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    GrupoModel buscar(Long id);

    @ApiResponses(@ApiResponse(responseCode = "201", description = "Grupo cadastrado."))
    GrupoModel adicionar(GrupoInput grupoInput);

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Grupo atualizado."),
            @ApiResponse(responseCode = "404", description = "Grupo nao encontrado.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    GrupoModel atualizar(Long id, GrupoInput grupoInput);

}

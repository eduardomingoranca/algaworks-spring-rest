package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.v1.model.PermissaoModel;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface GrupoPermissaoControllerOpenAPI {

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do grupo invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Grupo nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CollectionModel<PermissaoModel> listar(Long id);

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do grupo invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Grupo nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> associar(Long id, Long permissaoID);

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do grupo invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Grupo nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> desassociar(Long id, Long permissaoID);

}

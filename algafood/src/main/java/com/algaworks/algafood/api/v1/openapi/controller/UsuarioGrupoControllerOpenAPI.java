package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.v1.model.GrupoModel;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

public interface UsuarioGrupoControllerOpenAPI {

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do usuario invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CollectionModel<GrupoModel> listar(Long id);

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do usuario invalido", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> associar(Long id, Long grupoID);

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do usuario invalido", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    ResponseEntity<Void> desassociar(Long id, Long grupoID);

}

package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.v1.model.EstadoModel;
import com.algaworks.algafood.api.v1.model.input.EstadoInput;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
public interface EstadoControllerOpenAPI {

    CollectionModel<EstadoModel> listar();

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do estado invalido", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Estado nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    EstadoModel buscar(Long id);

    @ApiResponses(@ApiResponse(responseCode = "201", description = "Estado cadastrado"))
    EstadoModel adicionar(EstadoInput estadoInput);

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Estado atualizado"),
            @ApiResponse(responseCode = "404", description = "Estado nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    EstadoModel atualizar(Long id, EstadoInput estadoInput);

}

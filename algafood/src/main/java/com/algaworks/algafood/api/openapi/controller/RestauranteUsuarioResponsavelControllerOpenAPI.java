package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.model.UsuarioModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenAPI {

    @ApiOperation("Lista os usuarios associadas a restaurante")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do restaurante invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    List<UsuarioModel> listar(@ApiParam(value = "Codigo de um restaurante", required = true) Long id);


    @ApiOperation("Associacao de restaurante com usuario responsavel")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do restaurante invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    void associar(@ApiParam(value = "Codigo de um restaurante", required = true) Long id,
                  @ApiParam(value = "Codigo do usuario responsavel", required = true) Long usuarioID);


    @ApiOperation("Desassociacao de restaurante com usuario responsavel")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do restaurante invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    void desassociar(@ApiParam(value = "Codigo de um restaurante", required = true) Long id,
                     @ApiParam(value = "Codigo do usuario responsavel", required = true) Long usuarioID);

}

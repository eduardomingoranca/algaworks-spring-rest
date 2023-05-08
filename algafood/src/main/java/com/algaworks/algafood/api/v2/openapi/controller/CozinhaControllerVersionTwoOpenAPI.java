package com.algaworks.algafood.api.v2.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.v2.model.CozinhaModelVersionTwo;
import com.algaworks.algafood.api.v2.model.input.CozinhaInputVersionTwo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

@Api(tags = "Cozinhas")
public interface CozinhaControllerVersionTwoOpenAPI {

    @ApiOperation("Lista as cozinhas com paginacao")
    PagedModel<CozinhaModelVersionTwo> listar(Pageable pageable);

    @ApiOperation("Busca uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da cozinha invalida.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Cozinha nao encontrada", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CozinhaModelVersionTwo buscar(@ApiParam(value = "ID de uma cozinha", required = true) Long id);

    @ApiOperation("Cadastra uma cozinha")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "Cozinha cadastrada"))
    CozinhaModelVersionTwo adicionar(@ApiParam(name = "corpo", value = "Representacao de uma nova cozinha",
            required = true)
                                     CozinhaInputVersionTwo cozinhaInputVersionTwo);

    @ApiOperation("Atualiza uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cozinha atualizada."),
            @ApiResponse(responseCode = "404", description = "Cozinha nao encontrada", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CozinhaModelVersionTwo atualizar(@ApiParam(value = "ID de uma cozinha", required = true) Long id,
                                     @ApiParam(name = "corpo", value = "Representacao de uma nova cozinha",
                                             required = true) CozinhaInputVersionTwo cozinhaInputVersionTwo);

    @ApiOperation("Remove uma cozinha por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da cozinha invalida.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Cozinha nao encontrada", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    void remover(@ApiParam(value = "ID de uma cozinha", required = true) Long id);

}

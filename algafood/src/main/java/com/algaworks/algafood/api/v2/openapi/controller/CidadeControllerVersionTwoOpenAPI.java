package com.algaworks.algafood.api.v2.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.v2.model.CidadeModelVersionTwo;
import com.algaworks.algafood.api.v2.model.input.CidadeInputVersionTwo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.hateoas.CollectionModel;

@Api(tags = "Cidades")
public interface CidadeControllerVersionTwoOpenAPI {
    @ApiOperation("Lista as cidades")
    CollectionModel<CidadeModelVersionTwo> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da cidade invalida.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Cidade nao encontrada", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CidadeModelVersionTwo buscar(@ApiParam(value = "ID de uma cidade", required = true) Long id);

    @ApiOperation("Cadastra uma cidade")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "Cidade cadastrada"))
    CidadeModelVersionTwo adicionar(@ApiParam(name = "corpo", value = "Representacao de uma nova cidade",
            required = true)
                                    CidadeInputVersionTwo cidadeInputVersionTwo);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cidade atualizada."),
            @ApiResponse(responseCode = "404", description = "Cidade nao encontrada", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CidadeModelVersionTwo atualizar(@ApiParam(value = "ID de uma cidade", required = true) Long id,
                                    @ApiParam(name = "corpo", value = "Representacao de uma cidade com os novos dados",
                                            required = true)
                                    CidadeInputVersionTwo cidadeInputVersionTwo);
}

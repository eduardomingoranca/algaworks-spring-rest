package com.algaworks.algafood.api.v2.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.v2.model.CozinhaModelVersionTwo;
import com.algaworks.algafood.api.v2.model.input.CozinhaInputVersionTwo;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface CozinhaControllerVersionTwoOpenAPI {

    PagedModel<CozinhaModelVersionTwo> listar(Pageable pageable);

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da cozinha invalida.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Cozinha nao encontrada", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CozinhaModelVersionTwo buscar(Long id);

    @ApiResponses(@ApiResponse(responseCode = "201", description = "Cozinha cadastrada"))
    CozinhaModelVersionTwo adicionar(CozinhaInputVersionTwo cozinhaInputVersionTwo);

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cozinha atualizada."),
            @ApiResponse(responseCode = "404", description = "Cozinha nao encontrada", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CozinhaModelVersionTwo atualizar(Long id, CozinhaInputVersionTwo cozinhaInputVersionTwo);

    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da cozinha invalida.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Cozinha nao encontrada", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    void remover(Long id);

}

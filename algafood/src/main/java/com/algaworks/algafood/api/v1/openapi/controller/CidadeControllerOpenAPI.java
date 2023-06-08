package com.algaworks.algafood.api.v1.openapi.controller;


import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.v1.model.CidadeModel;
import com.algaworks.algafood.api.v1.model.input.CidadeInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Cidades")
public interface CidadeControllerOpenAPI {

    @Operation(summary = "Lista as cidades")
    CollectionModel<CidadeModel> listar();

    @Operation(summary = "Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "ID da cidade invalida.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Cidade nao encontrada", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CidadeModel buscar(Long id);

    @Operation(summary = "Cadastra uma cidade")
    @ApiResponses(@ApiResponse(responseCode = "201", description = "Cidade cadastrada"))
    CidadeModel adicionar(CidadeInput cidadeInput);

    @Operation(summary = "Atualizado uma cidade por ID", description = "Cadastro de uma cidade, " +
            "necessita de um estado e um nome valido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cidade atualizada."),
            @ApiResponse(responseCode = "404", description = "Cidade nao encontrada", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    CidadeModel atualizar(Long id, CidadeInput cidadeInput);
}

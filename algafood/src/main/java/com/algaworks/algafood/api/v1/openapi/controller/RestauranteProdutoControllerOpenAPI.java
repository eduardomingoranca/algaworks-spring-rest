package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.ProdutoModel;
import com.algaworks.algafood.api.v1.model.input.ProdutoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Produtos")
public interface RestauranteProdutoControllerOpenAPI {

    @Operation(summary = "Lista os produtos de um restaurante", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do restaurante invalido", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
    })
    CollectionModel<ProdutoModel> listar(@Parameter(description = "ID do restaurante", example = "1", required = true) Long id,
                                         @Parameter(description = "Incluir inativos", example = "false") Boolean incluirInativos);

    @Operation(summary = "Busca um produto de um restaurante", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto invalido", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
            @ApiResponse(responseCode = "404", description = "Produto de restaurante nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
    })
    ProdutoModel buscar(@Parameter(description = "ID do restaurante", example = "1", required = true) Long id,
                        @Parameter(description = "ID do produto", example = "1", required = true) Long produtoID);

    @Operation(summary = "Cadastra um produto de um restaurante", responses = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado"),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
    })
    ProdutoModel adicionar(@Parameter(description = "ID do restaurante", example = "1", required = true) Long id,
                           @RequestBody(description = "Representacao de um novo produto", required = true)
                           ProdutoInput produtoInput);

    @Operation(summary = "Atualiza um produto de um restaurante", responses = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado"),
            @ApiResponse(responseCode = "404", description = "Produto de restaurante nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
    })
    ProdutoModel atualizar(@Parameter(description = "ID do restaurante", example = "1", required = true) Long id,
                           @Parameter(description = "ID do produto", example = "1", required = true) Long produtoID,
                           @RequestBody(description = "Representacao de um produto com os novos dados", required = true)
                           ProdutoInput produtoInput);

}

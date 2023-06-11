package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.IOException;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Produtos")
public interface RestauranteProdutoFotoControllerOpenAPI {

    @Operation(summary = "Atualiza a foto do produto de um restaurante")
    FotoProdutoModel atualizarFoto(@Parameter(description = "Id do restaurante", example = "1", required = true) Long id,
                                   @Parameter(description = "Id do produto", example = "2", required = true) Long produtoId,
                                   @RequestBody(required = true) FotoProdutoInput fotoProdutoInput) throws IOException;


    @Operation(summary = "Busca a foto do produto de um restaurante", responses = {
            @ApiResponse(responseCode = "200", content = {
                    @Content(mediaType = "application/json",
                            schema = @Schema(implementation = FotoProdutoModel.class)),
                    @Content(mediaType = "image/jpeg", schema = @Schema(type = "string", format = "binary")),
                    @Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))
            }),
            @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto invalido", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
            @ApiResponse(responseCode = "404", description = "Foto de produto nao encontrada", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            })
    })
    FotoProdutoModel buscarFoto(@Parameter(description = "ID do restaurante", example = "1", required = true) Long id,
                                @Parameter(description = "ID do produto", example = "1", required = true) Long produtoId);

    @Operation(hidden = true)
    ResponseEntity<Object> servirFoto(Long id, Long produtoId, String acceptHeader)
            throws HttpMediaTypeNotAcceptableException;

    @Operation(summary = "Exclui a foto do produto de um restaurante", responses = {
            @ApiResponse(responseCode = "204", description = "Foto do produto excluida"),
            @ApiResponse(responseCode = "400", description = "ID do restaurante ou produto invalido", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
            @ApiResponse(responseCode = "404", description = "Foto de produto nao encontrada", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
    })
    ResponseEntity<FotoProdutoModel> remover(Long id, Long produtoId);

}

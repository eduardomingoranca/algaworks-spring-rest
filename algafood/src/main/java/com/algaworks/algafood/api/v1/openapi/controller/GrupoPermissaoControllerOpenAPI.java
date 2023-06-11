package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.PermissaoModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Grupos")
public interface GrupoPermissaoControllerOpenAPI {

    @Operation(summary = "Lista as permissoes associadas a um grupo", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "400", description = "ID do grupo invalido", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
            @ApiResponse(responseCode = "404", description = "Grupo nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
    })
    CollectionModel<PermissaoModel> listar(@Parameter(description = "ID de um grupo", example = "1", required = true)
                                           Long id);

    @Operation(summary = "Associacao de permissao com grupo", responses = {
            @ApiResponse(responseCode = "204", description = "Associacao realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Grupo ou permissao nao encontrada", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
    })
    ResponseEntity<Void> associar(@Parameter(description = "ID de um grupo", example = "1", required = true) Long id,
                                  @Parameter(description = "ID de uma permissao", example = "1", required = true) Long permissaoID);

    @Operation(summary = "Desassociacao de permissao com grupo", responses = {
            @ApiResponse(responseCode = "204", description = "Desassociacao realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Grupo ou permissao nao encontrada", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
    })
    ResponseEntity<Void> desassociar(@Parameter(description = "ID de um grupo", example = "1", required = true) Long id,
                                     @Parameter(description = "ID de uma permissao", example = "1", required = true) Long permissaoID);

}

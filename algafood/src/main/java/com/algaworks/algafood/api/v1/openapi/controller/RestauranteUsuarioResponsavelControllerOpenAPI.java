package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.UsuarioModel;
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
@Tag(name = "Restaurantes")
public interface RestauranteUsuarioResponsavelControllerOpenAPI {

    @Operation(summary = "Lista os usuarios responsaveis associados a restaurante", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Restaurante nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
    })
    CollectionModel<UsuarioModel> listar(@Parameter(description = "ID do restaurante", example = "1", required = true)
                                         Long id);

    @Operation(summary = "Associacao de restaurante com usuario responsavel", responses = {
            @ApiResponse(responseCode = "204", description = "Associacao realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou usuario nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
    })
    ResponseEntity<Void> associar(@Parameter(description = "ID do restaurante", example = "1", required = true) Long id,
                                  @Parameter(description = "ID do usuario", example = "1", required = true) Long usuarioID);

    @Operation(summary = "Desassociacao de restaurante com usuario responsavel", responses = {
            @ApiResponse(responseCode = "204", description = "Desassociacao realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Restaurante ou usuario nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
    })
    ResponseEntity<Void> desassociar(@Parameter(description = "ID do restaurante", example = "1", required = true) Long id,
                                     @Parameter(description = "ID do usuario", example = "1", required = true) Long usuarioID);

}

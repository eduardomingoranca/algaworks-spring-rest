package com.algaworks.algafood.api.v1.openapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Pedidos")
public interface FluxoPedidoControllerOpenAPI {

    @Operation(summary = "Confirmacao de pedido", responses = {
            @ApiResponse(responseCode = "204", description = "Pedido confirmado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
    })
    ResponseEntity<Void> confirmar(@Parameter(description = "Codigo de um pedido", example = "04813f77-79b5-11ec-9a17-0242ac1b0002",
            required = true) String codigo);

    @Operation(summary = "Registrar entrega de pedido", responses = {
            @ApiResponse(responseCode = "204", description = "Entrega de pedido registrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
    })
    ResponseEntity<Void> entregar(@Parameter(description = "Codigo de um pedido", example = "04813f77-79b5-11ec-9a17-0242ac1b0002",
            required = true) String codigo);

    @Operation(summary = "Cancelamento de pedido", responses = {
            @ApiResponse(responseCode = "204", description = "Pedido cancelado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))
            }),
    })
    ResponseEntity<Void> cancelar(@Parameter(description = "Código de um pedido", example = "04813f77-79b5-11ec-9a17-0242ac1b0002",
            required = true) String codigo);

}

package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.core.springdoc.parameter.PageableParameter;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Pedidos")
public interface PedidoControllerOpenAPI {
    @Operation(
            summary = "Pesquisa os pedidos",
            parameters = {
                    @Parameter(in = QUERY, name = "clienteId",
                            description = "ID do cliente para filtro da pesquisa",
                            example = "1", schema = @Schema(type = "integer")),
                    @Parameter(in = QUERY, name = "restauranteId",
                            description = "ID do restaurante para filtro da pesquisa",
                            example = "1", schema = @Schema(type = "integer")),
                    @Parameter(in = QUERY, name = "dataCriacaoInicio",
                            description = "Data/hora de criacao inicial para filtro da pesquisa",
                            example = "2023-12-01T00:00:00Z", schema = @Schema(type = "string", format = "date-time")),
                    @Parameter(in = QUERY, name = "dataCriacaoFim",
                            description = "Data/hora de criacao final para filtro da pesquisa",
                            example = "2023-12-02T23:59:59Z", schema = @Schema(type = "string", format = "date-time"))
            }
    )
    @PageableParameter
    PagedModel<PedidoResumoModel> pesquisar(@Parameter(hidden = true) PedidoFilter filtro,
                                            @Parameter(hidden = true) Pageable pageable);


    @Operation(summary = "Busca um pedido por codigo", responses = {
            @ApiResponse(responseCode = "200"),
            @ApiResponse(responseCode = "404", description = "Pedido nao encontrado", content = {
                    @Content(schema = @Schema(ref = "Problema"))}),
    })
    PedidoModel buscar(@Parameter(description = "Codigo de um pedido", example = "04813f77-79b5-11ec-9a17-0242ac1b0002",
            required = true) String codigo);

    @Operation(summary = "Registra um pedido", responses = {
            @ApiResponse(responseCode = "201", description = "Pedido registrado"),
    })
    PedidoModel adicionar(@RequestBody(description = "Representacao de um novo pedido", required = true)
                          PedidoInput pedidoInput);

}

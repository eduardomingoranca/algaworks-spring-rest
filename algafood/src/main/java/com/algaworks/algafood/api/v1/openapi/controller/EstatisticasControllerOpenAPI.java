package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.controller.EstatisticasController;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static io.swagger.v3.oas.annotations.enums.ParameterIn.QUERY;

@SecurityRequirement(name = "security_auth")
@Tag(name = "Estatisticas")
public interface EstatisticasControllerOpenAPI {

    @Operation(hidden = true)
    EstatisticasController.EstatisticasModel estatisticas();

    @Operation(
            summary = "Consulta estatisticas de vendas diarias",
            parameters = {
                    @Parameter(in = QUERY, name = "restauranteId", description = "ID do restaurante", example = "1",
                            schema = @Schema(type = "integer")),
                    @Parameter(in = QUERY, name = "dataCriacaoInicio", description = "Data/hora inicial da criacao do pedido",
                            example = "2023-12-01T00:00:00Z", schema = @Schema(type = "string", format = "date-time")),
                    @Parameter(in = QUERY, name = "dataCriacaoFim", description = "Data/hora final da criacao do pedido",
                            example = "2023-12-02T23:59:59Z", schema = @Schema(type = "string", format = "date-time"))
            },
            responses = {
                    @ApiResponse(responseCode = "200", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(schema =
                            @Schema(implementation = VendaDiaria.class))),
                            @Content(mediaType = "application/pdf", schema = @Schema(type = "string", format = "binary")),
                    }),
            }
    )
    List<VendaDiaria> consultarVendasDiarias(@Parameter(hidden = true) VendaDiariaFilter filtro,
                                             @Parameter(description = "Deslocamento de horario a ser considerado na " +
                                                     "consulta em relacao ao UTC", schema = @Schema(type = "string",
                                                     defaultValue = "+00:00")) String timeOffset);

    @Operation(hidden = true)
    ResponseEntity<byte[]> consultarVendasDiariasPDF(VendaDiariaFilter filtro, String timeOffset);
}

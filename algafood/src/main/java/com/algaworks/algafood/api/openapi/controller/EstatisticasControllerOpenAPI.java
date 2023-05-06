package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.controller.EstatisticasController;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Estatisticas")
public interface EstatisticasControllerOpenAPI {

    @ApiOperation(value = "Estatísticas", hidden = true)
    EstatisticasController.EstatisticasModel estatisticas();

    @ApiOperation("Consulta estatisticas de vendas diarias")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "restauranteId", value = "ID do restaurante", example = "1", dataType = "int"),
            @ApiImplicitParam(name = "dataCriacaoInicio", value = "Data/hora inicial da criacao do pedido",
                    example = "2023-01-01 18:09:02.70844", dataType = "date-time"),
            @ApiImplicitParam(name = "dataCriacaoFim", value = "Data/hora final da criacao do pedido",
                    example = "2023-01-02 19:10:03.80955", dataType = "date-time")
    })
    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
                                             @ApiParam(value = "Deslocamento de horario a ser considerado na consulta em relacao ao UTC",
                                                     defaultValue = "+00:00") String timeOffset);

    ResponseEntity<byte[]> consultarVendasDiariasPDF(VendaDiariaFilter filtro, String timeOffset);
}

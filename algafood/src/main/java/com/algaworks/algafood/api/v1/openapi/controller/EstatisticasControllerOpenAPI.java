package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.controller.EstatisticasController;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EstatisticasControllerOpenAPI {

    EstatisticasController.EstatisticasModel estatisticas();

    List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);

    ResponseEntity<byte[]> consultarVendasDiariasPDF(VendaDiariaFilter filtro, String timeOffset);
}

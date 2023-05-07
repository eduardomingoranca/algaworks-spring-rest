package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.openapi.controller.EstatisticasControllerOpenAPI;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.query.VendaQueryService;
import com.algaworks.algafood.domain.service.report.VendaReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.MediaType.*;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping(path = "/v1/estatisticas")
public class EstatisticasController implements EstatisticasControllerOpenAPI {
    @Autowired
    private VendaQueryService vendaQueryService;

    @Autowired
    private VendaReportService vendaReportService;

    @Autowired
    private AlgaLinks algaLinks;

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public EstatisticasModel estatisticas() {
        EstatisticasModel estatisticasModel = new EstatisticasModel();

        estatisticasModel.add(algaLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));

        return estatisticasModel;
    }

    @Override
    @GetMapping(value = "/vendas-diarias", produces = APPLICATION_JSON_VALUE)
    public List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro,
                                                    @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
    }

    @Override
    @GetMapping(value = "/vendas-diarias", produces = APPLICATION_PDF_VALUE)
    public ResponseEntity<byte[]> consultarVendasDiariasPDF(VendaDiariaFilter filtro,
                                                            @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
        byte[] bytesPDF = vendaReportService.emitirVendasDiarias(filtro, timeOffset);

        // indicando que o conteudo que esta sendo retornado
        // na resposta da requisicao deve ser baixado
        HttpHeaders headers = new HttpHeaders();
        headers.add(CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");

        return ok()
                .contentType(APPLICATION_PDF)
                .headers(headers)
                .body(bytesPDF);
    }

    public static class EstatisticasModel extends RepresentationModel<EstatisticasModel> {
    }

}

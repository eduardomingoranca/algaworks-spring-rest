package com.algaworks.algafood.infrastructure.service.report;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.service.report.VendaReportService;
import org.springframework.stereotype.Service;

@Service
public class VendaReportServicePDF implements VendaReportService {

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
        return null;
    }

}

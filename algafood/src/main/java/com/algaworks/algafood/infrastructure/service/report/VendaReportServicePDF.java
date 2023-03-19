package com.algaworks.algafood.infrastructure.service.report;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiaria;
import com.algaworks.algafood.domain.service.query.VendaQueryService;
import com.algaworks.algafood.domain.service.report.VendaReportService;
import com.algaworks.algafood.infrastructure.service.report.exception.ReportException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static net.sf.jasperreports.engine.JasperExportManager.exportReportToPdf;
import static net.sf.jasperreports.engine.JasperFillManager.fillReport;

@Service
public class VendaReportServicePDF implements VendaReportService {
    @Autowired
    private VendaQueryService vendaQueryService;

    @Override
    public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
        try {
            // buscando o fluxo de dados de um arquivo dentro do projeto
            InputStream inputStream = this.getClass().getResourceAsStream(
                    "/relatorios/vendas-diarias.jasper");

            // passando o parametro da localizacao
            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

            // obtendos a consulta das vendas diarias
            List<VendaDiaria> vendaDiarias = vendaQueryService.consultarVendasDiarias(filtro, timeOffset);

            // fonte de dados de onde vem os dados do relatorios
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(vendaDiarias);

            // gerenciamento de preenchimento de relatorios jasper reports
            JasperPrint jasperPrint = fillReport(inputStream, parametros, dataSource);

            // exportando para PDF retornando um array de bytes
            return exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            throw new ReportException("Nao foi possivel emitir relatorio de vendas diarias", e);
        }
    }

}

package com.algaworks.algafood.domain.filter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME;

@Setter
@Getter
public class PedidoFilter {
    private Long clienteId;
    private Long restauranteId;

    @DateTimeFormat(iso = DATE_TIME)
    private OffsetDateTime dataCriacaoInicio;

    @DateTimeFormat(iso = DATE_TIME)
    private OffsetDateTime dataCriacaoFim;

}

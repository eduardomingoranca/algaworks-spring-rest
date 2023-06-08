package com.algaworks.algafood.domain.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
public class VendaDiaria {
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date data;
    private Long totalVendas;

    private BigDecimal totalFaturado;

}

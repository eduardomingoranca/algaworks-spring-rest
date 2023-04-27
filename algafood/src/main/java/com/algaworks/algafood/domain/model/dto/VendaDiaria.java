package com.algaworks.algafood.domain.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@Setter
@Getter
public class VendaDiaria {
    @ApiModelProperty(example = "2023-04-27")
    private Date data;
    @ApiModelProperty(example = "1")
    private Long totalVendas;

    @ApiModelProperty(example = "120")
    private BigDecimal totalFaturado;

}

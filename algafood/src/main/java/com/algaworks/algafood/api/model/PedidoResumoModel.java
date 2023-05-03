package com.algaworks.algafood.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Setter
@Getter
public class PedidoResumoModel extends RepresentationModel<PedidoResumoModel> {
    @ApiModelProperty(example = "f9981ca4-a785-47fa-8631-5fb388754d63")
    private String codigo;

    @ApiModelProperty(example = "298.90")
    private BigDecimal subtotal;

    @ApiModelProperty(example = "10.00")
    private BigDecimal taxaFrete;

    @ApiModelProperty(example = "298.90")
    private BigDecimal valorTotal;

    @ApiModelProperty(example = "CRIADO")
    private String status;

    @ApiModelProperty(example = "2023-01-01 18:09:02.70844")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime dataCriacao;

    private RestauranteResumoModel restaurante;

    @ApiModelProperty(example = "Fergus Tipton")
    private String nomeCliente;

}

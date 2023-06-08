package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.exceptionhandler.model.Problem;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;

public interface PedidoControllerOpenAPI {
    PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro, Pageable pageable);


    @ApiResponses({
            @ApiResponse(responseCode = "400", description = "Codigo do pedido invalido.", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class))),
            @ApiResponse(responseCode = "404", description = "Pedido nao encontrado", content =
            @Content(mediaType = "application/json", schema = @Schema(implementation = Problem.class)))
    })
    PedidoModel buscar(String codigo);

    @ApiResponses(@ApiResponse(responseCode = "201", description = "Pedido cadastrado"))
    PedidoModel adicionar(PedidoInput pedidoInput);

}

package com.algaworks.algafood.api.links;

import com.algaworks.algafood.api.controller.PedidoController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.TemplateVariable.VariableType.REQUEST_PARAM;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class AlgaLinks {
    public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", REQUEST_PARAM),
            new TemplateVariable("size", REQUEST_PARAM),
            new TemplateVariable("sort", REQUEST_PARAM));

    public Link linkToPedidos() {
        TemplateVariable clienteId = new TemplateVariable("clienteId", REQUEST_PARAM);
        TemplateVariable restauranteId = new TemplateVariable("restauranteId", REQUEST_PARAM);
        TemplateVariable dataCriacaoInicio = new TemplateVariable("dataCriacaoInicio", REQUEST_PARAM);
        TemplateVariable dataCriacaoFim = new TemplateVariable("dataCriacaoFim", REQUEST_PARAM);

        TemplateVariables filtroVariables = new TemplateVariables(clienteId, restauranteId, dataCriacaoInicio,
                dataCriacaoFim);

        String pedidosURL = linkTo(PedidoController.class).toUri().toString();

        return Link.of(UriTemplate.of(pedidosURL, PAGINACAO_VARIABLES.concat(filtroVariables)), "pedidos");
    }

}

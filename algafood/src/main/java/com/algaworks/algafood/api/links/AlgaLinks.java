package com.algaworks.algafood.api.links;

import com.algaworks.algafood.api.controller.*;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

import static java.lang.String.valueOf;
import static org.springframework.hateoas.IanaLinkRelations.SELF;
import static org.springframework.hateoas.TemplateVariable.VariableType.REQUEST_PARAM;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlgaLinks {
    public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
            new TemplateVariable("page", REQUEST_PARAM),
            new TemplateVariable("size", REQUEST_PARAM),
            new TemplateVariable("sort", REQUEST_PARAM));

    public Link linkToPedidos(String rel) {
        TemplateVariable clienteId = new TemplateVariable("clienteId", REQUEST_PARAM);
        TemplateVariable restauranteId = new TemplateVariable("restauranteId", REQUEST_PARAM);
        TemplateVariable dataCriacaoInicio = new TemplateVariable("dataCriacaoInicio", REQUEST_PARAM);
        TemplateVariable dataCriacaoFim = new TemplateVariable("dataCriacaoFim", REQUEST_PARAM);

        TemplateVariables filtroVariables = new TemplateVariables(clienteId, restauranteId, dataCriacaoInicio,
                dataCriacaoFim);

        String pedidosURL = linkTo(PedidoController.class).toUri().toString();

        return Link.of(UriTemplate.of(pedidosURL, PAGINACAO_VARIABLES.concat(filtroVariables)), rel);
    }

    public Link linkToConfirmacaoPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(FluxoPedidoController.class).confirmar(codigoPedido)).withRel(rel);
    }

    public Link linkToEntregaPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(FluxoPedidoController.class).entregar(codigoPedido)).withRel(rel);
    }

    public Link linkToCancelamentoPedido(String codigoPedido, String rel) {
        return linkTo(methodOn(FluxoPedidoController.class).cancelar(codigoPedido)).withRel(rel);
    }


    public Link linkToRestaurante(Long restauranteID, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .buscar(restauranteID)).withRel(rel);
    }

    public Link linkToRestaurante(Long restauranteID) {
        return linkToRestaurante(restauranteID, SELF.value());
    }

    public Link linkToCliente(Long clienteID, String rel) {
        return linkTo(methodOn(UsuarioController.class)
                .buscar(clienteID)).withRel(rel);
    }

    public Link linkToCliente(Long clienteID) {
        return linkToCliente(clienteID, SELF.value());
    }

    public Link linkToFormaPagamento(Long formaPagamentoID, String rel) {
        return linkTo(methodOn(FormaPagamentoController.class)
                .buscar(formaPagamentoID, null)).withRel(rel);
    }

    public Link linkToFormaPagamento(Long formaPagamentoID) {
        return linkToFormaPagamento(formaPagamentoID, SELF.value());
    }

    public Link linkToCidade(Long cidadeID, String rel) {
        return linkTo(methodOn(CidadeController.class)
                .buscar(cidadeID)).withRel(rel);
    }

    public Link linkToCidade(Long cidadeID) {
        return linkToCidade(cidadeID, SELF.value());
    }

    public Link linkToCidades(String rel) {
        return linkTo(methodOn(CidadeController.class)
                .listar()).withRel(rel);
    }

    public Link linkToProduto(Long itemID, Long restauranteID, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .buscar(restauranteID, itemID))
                .withRel(rel);
    }

    public Link linkToProduto(Long itemID, Long restauranteID) {
        return linkToProduto(itemID, restauranteID, SELF.value());
    }

    public Link linkToProdutos(Long restauranteId, String rel) {
        return linkTo(methodOn(RestauranteProdutoController.class)
                .listar(restauranteId, null)).withRel(rel);
    }

    public Link linkToProdutos(Long restauranteId) {
        return linkToProdutos(restauranteId, SELF.value());
    }


    public Link linkToEstado(Long estadoID, String rel) {
        return linkTo(methodOn(EstadoController.class)
                .buscar(estadoID)).withRel(rel);
    }

    public Link linkToEstado(Long estadoID) {
        return linkToEstado(estadoID, SELF.value());
    }

    public Link linkToUsuarios(String rel) {
        return linkTo(methodOn(UsuarioController.class)
                .listar())
                .withRel(rel);
    }

    public Link linkToGruposUsuario(Long usuarioID, String rel) {
        return linkTo(methodOn(UsuarioGrupoController.class)
                .listar(usuarioID))
                .withRel(rel);
    }

    public Link linkToGruposUsuario(Long usuarioID) {
        return linkToGruposUsuario(usuarioID, SELF.value());
    }

    public Link linkToResponsaveisRestaurante(Long restauranteID, String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                .listar(restauranteID)).withRel(rel);
    }

    public Link linkToResponsaveisRestaurante(Long restauranteID) {
        return linkToResponsaveisRestaurante(restauranteID, SELF.value());
    }

    public Link linkToEstados(String rel) {
        return linkTo(methodOn(EstadoController.class)
                .listar())
                .withRel(rel);
    }

    public Link linkToCozinhas(String rel) {
        return linkTo(CozinhaController.class).withRel(rel);
    }

    public Link linkToRestaurantes(String rel) {
        TemplateVariable projecao = new TemplateVariable("projecao", REQUEST_PARAM);
        TemplateVariables filtroVariables = new TemplateVariables(projecao);

        String restaurantesURL = linkTo(RestauranteController.class).toUri().toString();

        return Link.of(UriTemplate.of(restaurantesURL, filtroVariables), rel);
    }

    public Link linkToRestaurantes() {
        TemplateVariable projecao = new TemplateVariable("projecao", REQUEST_PARAM);
        TemplateVariables filtroVariables = new TemplateVariables(projecao);

        String restaurantesURL = linkTo(RestauranteController.class).toUri().toString();

        return Link.of(valueOf(UriTemplate.of(restaurantesURL, filtroVariables)));
    }

    public Link linkToCozinha(Long cozinhaID, String rel) {
        return linkTo(methodOn(CozinhaController.class)
                .buscar(cozinhaID))
                .withRel(rel);
    }

    public Link linkToCozinha(Long cozinhaID) {
        return linkToCozinha(cozinhaID, SELF.value());
    }

    public Link linkToFormaPagamentoRestaurante(Long restauranteID, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .listar(restauranteID))
                .withRel(rel);
    }

    public Link linkToFormaPagamentoRestaurante(Long restauranteID) {
        return linkToFormaPagamentoRestaurante(restauranteID, SELF.value());
    }

    public Link linkToAtivacaoRestaurante(Long restauranteID, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .ativar(restauranteID))
                .withRel(rel);
    }

    public Link linkToInativacaoRestaurante(Long restauranteID, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .inativar(restauranteID))
                .withRel(rel);
    }

    public Link linkToAberturaRestaurante(Long restauranteID, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .abrir(restauranteID))
                .withRel(rel);
    }

    public Link linkToFechamentoRestaurante(Long restauranteID, String rel) {
        return linkTo(methodOn(RestauranteController.class)
                .fechar(restauranteID))
                .withRel(rel);
    }

    public Link linkToFormasPagamento(String rel) {
        return linkTo(FormaPagamentoController.class).withRel(rel);
    }

    public Link linkToFormasPagamento() {
        return linkTo(FormaPagamentoController.class).withSelfRel();
    }

    public Link linkToRestauranteFormaPagamentoDesassociacao(Long restauranteID, Long formaPagamentoID, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .desassociar(restauranteID, formaPagamentoID)).withRel(rel);
    }

    public Link linkToRestauranteFormaPagamentoAssociacao(Long restauranteID, String rel) {
        return linkTo(methodOn(RestauranteFormaPagamentoController.class)
                .associar(restauranteID, null)).withRel(rel);
    }

    public Link linkToRestauranteUsuarioResponsavelAssociacao(Long restauranteID, String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                .associar(restauranteID, null)).withRel(rel);
    }

    public Link linkToRestauranteUsuarioResponsavelDesassociar(Long restauranteID, Long usuarioID,
                                                               String rel) {
        return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                .desassociar(restauranteID, usuarioID)).withRel(rel);
    }

    public Link linkToFotoProduto(Long restauranteID, Long produtoID, String rel) {
        return linkTo(methodOn(RestauranteProdutoFotoController.class)
                .buscarFoto(restauranteID, produtoID)).withRel(rel);
    }

    public Link linkToFotoProduto(Long restauranteID, Long produtoID) {
        return linkToFotoProduto(restauranteID, produtoID, SELF.value());
    }

}

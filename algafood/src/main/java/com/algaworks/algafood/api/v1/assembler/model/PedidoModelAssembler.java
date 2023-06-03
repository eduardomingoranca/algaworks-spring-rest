package com.algaworks.algafood.api.v1.assembler.model;

import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import static com.algaworks.algafood.domain.enumeration.StatusPedido.*;

@Component
public class PedidoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoModel> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public PedidoModelAssembler() {
        super(PedidoController.class, PedidoModel.class);
    }

    @Override
    public PedidoModel toModel(Pedido pedido) {
        PedidoModel pedidoModel = createModelWithId(pedido.getId(), pedido);
        modelMapper.map(pedido, pedidoModel);
        // Nao usei o metodo algaSecurity.podePesquisarPedidos(clienteId, restauranteId) aqui,
        // porque na geracao do link, nao temos o id do cliente e do restaurante,
        // entao precisamos saber apenas se a requisicao esta autenticada e tem o escopo de leitura

        if (algaSecurity.podePesquisarPedidos())
            pedidoModel.add(algaLinks.linkToPedidos("pedidos"));

        if (algaSecurity.podeGerenciarPedidos(pedido.getCodigo())) {
            if (podeSerConfirmado(pedido))
                pedidoModel.add(algaLinks.linkToConfirmacaoPedido(pedidoModel.getCodigo(), "confirmar"));

            if (podeSerCancelado(pedido))
                pedidoModel.add(algaLinks.linkToCancelamentoPedido(pedidoModel.getCodigo(), "cancelar"));

            if (podeSerEntregue(pedido))
                pedidoModel.add(algaLinks.linkToEntregaPedido(pedidoModel.getCodigo(), "entregar"));
        }

        if (algaSecurity.podeConsultarRestaurantes())
            pedidoModel.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));

        if (algaSecurity.podeConsultarUsuariosGruposPermissoes())
            pedidoModel.getCliente().add(algaLinks.linkToCliente(pedido.getCliente().getId()));

        if (algaSecurity.podeConsultarFormasPagamento())
            // Passando null no segundo argumento, porque eh indiferente para a
            // construcao da URL do recurso de forma de pagamento
            pedidoModel.getFormaPagamento().add(algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));

        if (algaSecurity.podeConsultarCidades())
            pedidoModel.getEnderecoEntrega().getCidade().add(algaLinks.linkToCidade(pedido.getEnderecoEntrega()
                    .getCidade().getId()));

        // Quem pode consultar restaurantes, tambem pode consultar os produtos dos restaurantes
        if (algaSecurity.podeConsultarRestaurantes())
            pedidoModel.getItens().forEach(item -> item.add(algaLinks.linkToProduto(pedidoModel.getRestaurante().getId(),
                    item.getProdutoId(), "produto")));

        return pedidoModel;
    }

    private boolean podeSerConfirmado(Pedido pedido) {
        return pedido.getStatus().podeAlterarPara(CONFIRMADO);
    }

    private boolean podeSerEntregue(Pedido pedido) {
        return pedido.getStatus().podeAlterarPara(ENTREGUE);
    }

    private boolean podeSerCancelado(Pedido pedido) {
        return pedido.getStatus().podeAlterarPara(CANCELADO);
    }

}

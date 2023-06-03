package com.algaworks.algafood.api.v1.assembler.model;

import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.links.AlgaLinks;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.core.security.AlgaSecurity;
import com.algaworks.algafood.domain.model.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoModelAssembler extends RepresentationModelAssemblerSupport<Pedido, PedidoResumoModel> {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    @Autowired
    private AlgaSecurity algaSecurity;

    public PedidoResumoModelAssembler() {
        super(PedidoController.class, PedidoResumoModel.class);
    }

    @Override
    public PedidoResumoModel toModel(Pedido pedido) {
        PedidoResumoModel pedidoResumoModel = createModelWithId(pedido.getId(), pedido);
        modelMapper.map(pedido, pedidoResumoModel);

        if (algaSecurity.podePesquisarPedidos())
            pedidoResumoModel.add(algaLinks.linkToPedidos("pedidos"));

        if (algaSecurity.podeConsultarRestaurantes())
            pedidoResumoModel.getRestaurante().add(algaLinks.linkToRestaurante(pedido
                    .getRestaurante().getId()));

        if (algaSecurity.podeConsultarUsuariosGruposPermissoes())
            pedidoResumoModel.getCliente().add(algaLinks.linkToCliente(pedido.getCliente().getId()));

        return pedidoResumoModel;
    }

}

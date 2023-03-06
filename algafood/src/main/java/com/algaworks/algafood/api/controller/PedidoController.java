package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.model.PedidoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.CadastroPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private CadastroPedidoService cadastroPedido;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @GetMapping
    public List<PedidoModel> listar() {
        List<Pedido> pedidos = cadastroPedido.listar();

        return pedidoModelAssembler.toCollectionModel(pedidos);
    }

    @GetMapping("/{pedidoID}")
    public PedidoModel buscar(@PathVariable("pedidoID") Long id) {
        Pedido pedido = cadastroPedido.buscarOuFalhar(id);

        return pedidoModelAssembler.toModel(pedido);
    }

}

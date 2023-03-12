package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.input.disassembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.assembler.model.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.model.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.exception.*;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.CadastroPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private CadastroPedidoService cadastroPedido;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    @GetMapping
    public List<PedidoResumoModel> listar() {
        List<Pedido> pedidos = cadastroPedido.listar();

        return pedidoResumoModelAssembler.toCollectionModel(pedidos);
    }

    @GetMapping("/{pedidoID}")
    public PedidoModel buscar(@PathVariable("pedidoID") Long id) {
        Pedido pedido = cadastroPedido.buscarOuFalhar(id);

        return pedidoModelAssembler.toModel(pedido);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public PedidoModel adicionar(@RequestBody @Valid PedidoInput pedidoInput) {
        try {
            Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
            Pedido salvarPedido = cadastroPedido.salvar(pedido);

            return pedidoModelAssembler.toModel(salvarPedido);
        } catch (RestauranteNaoEncontradoException | FormaPagamentoNaoEncontradaException |
        UsuarioNaoEncontradoException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

}

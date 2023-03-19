package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.input.disassembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.assembler.model.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.model.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.domain.exception.*;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.filter.PedidoFilter;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.algaworks.algafood.core.data.PageableTranslator.translate;
import static com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs.usandoFiltro;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    @GetMapping
    public Page<PedidoResumoModel> pesquisar(PedidoFilter filtro,
                                             @PageableDefault(size = 10) Pageable pageable) {
        // converter para um novo pageable com propriedades da classe de dominio/entidade
        pageable = traduzirPageable(pageable);

        Page<Pedido> pedidosPage = emissaoPedido.listar(usandoFiltro(filtro), pageable);

        List<PedidoResumoModel> pedidosResumoModel = pedidoResumoModelAssembler
                .toCollectionModel(pedidosPage.getContent());

        return new PageImpl<>(pedidosResumoModel, pageable, pedidosPage.getTotalElements());
    }

    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable("codigoPedido") String codigo) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigo);

        return pedidoModelAssembler.toModel(pedido);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public PedidoModel adicionar(@RequestBody @Valid PedidoInput pedidoInput) {
        try {
            Pedido pedido = pedidoInputDisassembler.toDomainObject(pedidoInput);
            Pedido salvarPedido = emissaoPedido.salvar(pedido);

            return pedidoModelAssembler.toModel(salvarPedido);
        } catch (RestauranteNaoEncontradoException | FormaPagamentoNaoEncontradaException |
                 UsuarioNaoEncontradoException | CidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    private Pageable traduzirPageable(Pageable pageable) {
        // tambem poderia usar Map.of()
        ImmutableMap<String, String> mapeamento = ImmutableMap.of(
                "codigo", "codigo",
                "restaurante.nome", "restaurante.nome",
                "nomeCliente", "cliente.nome",
                "valorTotal", "valorTotal");

        return translate(pageable, mapeamento);
    }

}

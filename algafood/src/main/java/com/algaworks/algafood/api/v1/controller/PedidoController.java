package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.input.disassembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.v1.assembler.model.PedidoModelAssembler;
import com.algaworks.algafood.api.v1.assembler.model.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.v1.model.PedidoModel;
import com.algaworks.algafood.api.v1.model.PedidoResumoModel;
import com.algaworks.algafood.api.v1.model.input.PedidoInput;
import com.algaworks.algafood.api.v1.openapi.controller.PedidoControllerOpenAPI;
import com.algaworks.algafood.core.data.wrapper.PageWrapper;
import com.algaworks.algafood.core.security.annotation.CheckSecurity;
import com.algaworks.algafood.domain.exception.*;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.algaworks.algafood.infrastructure.repository.spec.PedidoSpecs.usandoFiltro;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/v1/pedidos", produces = APPLICATION_JSON_VALUE)
public class PedidoController implements PedidoControllerOpenAPI {
    @Autowired
    private EmissaoPedidoService emissaoPedido;

    @Autowired
    private PedidoModelAssembler pedidoModelAssembler;

    @Autowired
    private PedidoResumoModelAssembler pedidoResumoModelAssembler;

    @Autowired
    private PedidoInputDisassembler pedidoInputDisassembler;

    @Autowired
    private PagedResourcesAssembler<Pedido> pagedResourcesAssembler;

    @CheckSecurity.Pedidos.PodePesquisar
    @Override
    @GetMapping
    public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro,
                                                   @PageableDefault(size = 10) Pageable pageable) {
        // converter para um novo pageable com propriedades da classe de dominio/entidade
        Pageable pageableTraduzido = emissaoPedido.traduzirPageable(pageable);

        Page<Pedido> pedidosPage = emissaoPedido.listar(usandoFiltro(filtro), pageableTraduzido);

        pedidosPage = new PageWrapper<>(pedidosPage, pageable);

        return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
    }

    @CheckSecurity.Pedidos.PodeBuscar
    @Override
    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable("codigoPedido") String id) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(id);

        return pedidoModelAssembler.toModel(pedido);
    }

    @CheckSecurity.Pedidos.PodeCriar
    @Override
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

}

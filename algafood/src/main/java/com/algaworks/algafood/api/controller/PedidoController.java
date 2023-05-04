package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.input.disassembler.PedidoInputDisassembler;
import com.algaworks.algafood.api.assembler.model.PedidoModelAssembler;
import com.algaworks.algafood.api.assembler.model.PedidoResumoModelAssembler;
import com.algaworks.algafood.api.model.PedidoModel;
import com.algaworks.algafood.api.model.PedidoResumoModel;
import com.algaworks.algafood.api.model.input.PedidoInput;
import com.algaworks.algafood.api.openapi.controller.PedidoControllerOpenAPI;
import com.algaworks.algafood.core.data.wrapper.PageWrapper;
import com.algaworks.algafood.domain.exception.*;
import com.algaworks.algafood.domain.filter.PedidoFilter;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
@RequestMapping(value = "/pedidos", produces = APPLICATION_JSON_VALUE)
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

    @Override
    @ApiImplicitParams(@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por virgula",
            name = "campos", paramType = "query", type = "string"))
    @GetMapping
    public PagedModel<PedidoResumoModel> pesquisar(PedidoFilter filtro,
                                                   @PageableDefault(size = 10) Pageable pageable) {
        // converter para um novo pageable com propriedades da classe de dominio/entidade
        Pageable pageableTraduzido = emissaoPedido.traduzirPageable(pageable);

        Page<Pedido> pedidosPage = emissaoPedido.listar(usandoFiltro(filtro), pageableTraduzido);

        pedidosPage = new PageWrapper<>(pedidosPage, pageable);

        return pagedResourcesAssembler.toModel(pedidosPage, pedidoResumoModelAssembler);
    }

    @Override
    @ApiImplicitParams(@ApiImplicitParam(value = "Nomes das propriedades para filtrar na resposta, separados por virgula",
            name = "campos", paramType = "query", type = "string"))
    @GetMapping("/{codigoPedido}")
    public PedidoModel buscar(@PathVariable("codigoPedido") String codigo) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(codigo);

        return pedidoModelAssembler.toModel(pedido);
    }

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

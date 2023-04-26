package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.input.disassembler.ProdutoInputDisassembler;
import com.algaworks.algafood.api.assembler.model.ProdutoModelAssembler;
import com.algaworks.algafood.api.model.ProdutoModel;
import com.algaworks.algafood.api.model.input.ProdutoInput;
import com.algaworks.algafood.api.openapi.controller.RestauranteProdutoControllerOpenAPI;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteID}/produtos", produces = APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenAPI {
    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private ProdutoModelAssembler produtoModelAssembler;

    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private ProdutoInputDisassembler produtoInputDisassembler;

    @Override
    @GetMapping
    public List<ProdutoModel> listar(@PathVariable("restauranteID") Long id,
                                     @RequestParam(required = false) boolean incluirInativos) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);

        List<Produto> todosProdutos;

        if (incluirInativos) {
            // chamar consulta
            todosProdutos = cadastroProduto.buscarTodosOsProdutosPorRestaurante(restaurante);
        } else {
            // buscando produtos ativos
            todosProdutos = cadastroProduto.buscarTodosOsProdutosAtivosPorRestaurante(restaurante);
        }

        return produtoModelAssembler.toCollectionModel(todosProdutos);
    }

    @Override
    @GetMapping("/{produtoID}")
    public ProdutoModel buscar(@PathVariable("restauranteID") Long id,
                               @PathVariable Long produtoID) {
        Produto produto = cadastroProduto.buscarOuFalhar(id, produtoID);
        return produtoModelAssembler.toModel(produto);
    }

    @Override
    @PostMapping
    @ResponseStatus(CREATED)
    public ProdutoModel adicionar(@PathVariable("restauranteID") Long id,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
        Produto salvarProduto = cadastroProduto.salvar(produto, id);

        return produtoModelAssembler.toModel(salvarProduto);
    }

    @Override
    @PutMapping("/{produtoID}")
    public ProdutoModel atualizar(@PathVariable("restauranteID") Long id,
                                  @PathVariable Long produtoID,
                                  @RequestBody @Valid ProdutoInput produtoInput) {
        Produto produtoAtual = cadastroProduto.buscarOuFalhar(id, produtoID);

        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);

        Produto salvarProduto = cadastroProduto.salvar(produtoAtual, id);

        return produtoModelAssembler.toModel(salvarProduto);
    }

}

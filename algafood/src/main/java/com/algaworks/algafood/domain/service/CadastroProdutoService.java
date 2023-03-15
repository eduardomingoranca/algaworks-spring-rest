package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CadastroProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Transactional
    public Produto buscarOuFalhar(Long id, Long produtoID) {
        return produtoRepository.findById(id, produtoID)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(id, produtoID));
    }

    @Transactional
    public Produto salvar(Produto produto, Long id) {
        Restaurante restaurante = cadastroRestaurante.buscarOuFalhar(id);
        produto.setRestaurante(restaurante);

        return produtoRepository.save(produto);
    }

    @Transactional
    public List<Produto> buscarTodosOsProdutosAtivosPorRestaurante(Restaurante restaurante) {
        return produtoRepository.findAtivosByRestaurante(restaurante);
    }

    @Transactional
    public List<Produto> buscarTodosOsProdutosPorRestaurante(Restaurante restaurante) {
        return produtoRepository.findTodosByRestaurante(restaurante);
    }
}

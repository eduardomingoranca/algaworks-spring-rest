package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto) {
        Long restauranteId = getRestauranteId(fotoProduto);
        Long produtoId = fotoProduto.getProduto().getId();

        // excluir foto, se existir
        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoProdutoById(restauranteId, produtoId);

        // excluir foto
        fotoExistente.ifPresent(foto -> produtoRepository.delete(foto));

        return produtoRepository.save(fotoProduto);
    }

    private Long getRestauranteId(FotoProduto fotoProduto) {
        if (fotoProduto.getProduto() != null)
            return fotoProduto.getProduto().getRestaurante().getId();

        return null;
    }

}

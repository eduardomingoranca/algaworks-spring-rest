package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.domain.service.storage.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.Optional;

@Service
public class CatalogoFotoProdutoService {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private FotoStorageService fotoStorage;

    @Transactional
    public FotoProduto salvar(FotoProduto fotoProduto, InputStream dadosArquivo) {
        Long restauranteId = getRestauranteId(fotoProduto);
        Long produtoId = fotoProduto.getProduto().getId();
        // gerando o nome do arquivo
        String nomeNovoArquivo = fotoStorage.gerarNomeArquivo(fotoProduto.getNomeArquivo());
        String nomeArquivoExistente = null;

        // excluir foto, se existir
        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoProdutoById(restauranteId, produtoId);

        // excluir foto
        if (fotoExistente.isPresent()) {
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoExistente.get());
        }

        fotoProduto.setNomeArquivo(nomeNovoArquivo);
        fotoProduto = produtoRepository.save(fotoProduto);
        // salvando no banco de dados
        // descarregando/executando o insert no banco de dados no momento do save
        // descarregando tudo que tiver na fila do Entity Manager do JPA
        produtoRepository.flush();

        // armazenando a foto em um diretorio/pasta local
        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .nomeArquivo(fotoProduto.getNomeArquivo())
                .inputStream(dadosArquivo)
                .build();

        // substituindo o arquivo existente pelo novo
        fotoStorage.substituir(nomeArquivoExistente, novaFoto);

        return fotoProduto;
    }

    public FotoProduto buscarOuFalhar(Produto produto) {
        Long restauranteId = produto.getRestaurante().getId();
        Long produtoId = produto.getId();

        return produtoRepository.findFotoProdutoById(restauranteId, produtoId)
                .orElseThrow(() -> new FotoProdutoNaoEncontradaException(restauranteId, produtoId));
    }

    private Long getRestauranteId(FotoProduto fotoProduto) {
        if (fotoProduto.getProduto() != null)
            return fotoProduto.getProduto().getRestaurante().getId();

        return null;
    }

}

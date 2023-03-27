package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.assembler.model.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.model.FotoProdutoModel;
import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.storage.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.io.IOException;
import java.io.InputStream;

import static org.springframework.http.MediaType.*;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProduto;

    @Autowired
    private FotoProdutoModelAssembler fotoProdutoModelAssembler;

    @Autowired
    private FotoStorageService fotoStorage;

    @PutMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizarFoto(@PathVariable("restauranteId") Long id,
                                          @PathVariable Long produtoId,
                                          @Valid FotoProdutoInput fotoProdutoInput) throws IOException {
        Produto produto = cadastroProduto.buscarOuFalhar(id, produtoId);

        MultipartFile arquivo = fotoProdutoInput.getArquivo();

        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
        fotoProduto.setContentType(arquivo.getContentType());
        fotoProduto.setTamanho(arquivo.getSize());
        fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());

        FotoProduto fotoProdutoSalvar = catalogoFotoProduto.salvar(fotoProduto, arquivo.getInputStream());

        return fotoProdutoModelAssembler.toModel(fotoProdutoSalvar);
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public FotoProdutoModel buscarFoto(@PathVariable("restauranteId") Long id,
                                       @PathVariable Long produtoId) {
        Produto produto = cadastroProduto.buscarOuFalhar(id, produtoId);
        FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(produto);

        return fotoProdutoModelAssembler.toModel(fotoProduto);
    }

    @GetMapping(produces = IMAGE_JPEG_VALUE)
    public ResponseEntity<InputStreamResource> servirFoto(@PathVariable("restauranteId") Long id,
                                                          @PathVariable Long produtoId) {
        try {
            Produto produto = cadastroProduto.buscarOuFalhar(id, produtoId);
            FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(produto);

            // buscar os dados da imagem
            InputStream inputStream = fotoStorage.recuperar(fotoProduto.getNomeArquivo());

            return ok()
                    .contentType(IMAGE_JPEG)
                    .body(new InputStreamResource(inputStream));

        } catch (EntidadeNaoEncontradaException e) {
            return notFound().build();
        }
    }

}

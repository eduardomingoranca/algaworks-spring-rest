package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.assembler.model.FotoProdutoModelAssembler;
import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteProdutoFotoControllerOpenAPI;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CadastroProdutoService;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.storage.FotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.MediaType.*;
import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping(value = "/v1/restaurantes/{restauranteId}/produtos/{produtoId}/foto", produces = APPLICATION_JSON_VALUE)
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenAPI {
    @Autowired
    private CadastroProdutoService cadastroProduto;

    @Autowired
    private CatalogoFotoProdutoService catalogoFotoProduto;

    @Autowired
    private FotoProdutoModelAssembler fotoProdutoModelAssembler;

    @Autowired
    private FotoStorageService fotoStorage;

    @Override
    @PutMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public FotoProdutoModel atualizarFoto(@PathVariable("restauranteId") Long id,
                                          @PathVariable Long produtoId,
                                          @Valid FotoProdutoInput fotoProdutoInput,
                                          @RequestPart(required = true) MultipartFile arquivo) throws IOException {
        Produto produto = cadastroProduto.buscarOuFalhar(id, produtoId);

        FotoProduto fotoProduto = new FotoProduto();
        fotoProduto.setProduto(produto);
        fotoProduto.setDescricao(fotoProdutoInput.getDescricao());
        fotoProduto.setContentType(arquivo.getContentType());
        fotoProduto.setTamanho(arquivo.getSize());
        fotoProduto.setNomeArquivo(arquivo.getOriginalFilename());

        FotoProduto fotoProdutoSalvar = catalogoFotoProduto.salvar(fotoProduto, arquivo.getInputStream());

        return fotoProdutoModelAssembler.toModel(fotoProdutoSalvar);
    }

    @Override
    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public FotoProdutoModel buscarFoto(@PathVariable("restauranteId") Long id,
                                       @PathVariable Long produtoId) {
        Produto produto = cadastroProduto.buscarOuFalhar(id, produtoId);
        FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(produto);

        return fotoProdutoModelAssembler.toModel(fotoProduto);
    }

    @Override
    @GetMapping(produces = ALL_VALUE)
    public ResponseEntity<Object> servirFoto(@PathVariable("restauranteId") Long id,
                                             @PathVariable Long produtoId,
                                             @RequestHeader(name = "accept") String acceptHeader)
            throws HttpMediaTypeNotAcceptableException {
        try {
            Produto produto = cadastroProduto.buscarOuFalhar(id, produtoId);
            FotoProduto fotoProduto = catalogoFotoProduto.buscarOuFalhar(produto);

            // media type da foto do produto salva/armazenada
            MediaType mediaTypeFoto = parseMediaType(fotoProduto.getContentType());

            // obtendo uma lista do media type informado pelo header aceitas pela API
            List<MediaType> mediaTypesAceitas = parseMediaTypes(acceptHeader);

            catalogoFotoProduto.verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);

            // buscar os dados da imagem
            FotoStorageService.FotoRecuperada fotoRecuperada = fotoStorage.recuperar(fotoProduto.getNomeArquivo());

            if (fotoRecuperada.temUrl()) {
                return status(FOUND)
                        .header(LOCATION, fotoRecuperada.getUrl())
                        .build();
            } else {
                return ok()
                        .contentType(mediaTypeFoto)
                        .body(new InputStreamResource(fotoRecuperada.getInputStream()));
            }
        } catch (EntidadeNaoEncontradaException e) {
            return notFound().build();
        }
    }

    @Override
    @DeleteMapping
    public ResponseEntity<FotoProdutoModel> remover(@PathVariable("restauranteId") Long id,
                                                    @PathVariable Long produtoId) {
        catalogoFotoProduto.removerFoto(id, produtoId);
        return noContent().build();
    }

}

package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.model.FotoProdutoModel;
import com.algaworks.algafood.api.v1.model.input.FotoProdutoInput;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface RestauranteProdutoFotoControllerOpenAPI {


    FotoProdutoModel atualizarFoto(Long id, Long produtoId, FotoProdutoInput fotoProdutoInput,
                                   MultipartFile arquivo) throws IOException;


    FotoProdutoModel buscarFoto(Long id, Long produtoId);

    ResponseEntity<Object> servirFoto(Long id, Long produtoId, String acceptHeader)
            throws HttpMediaTypeNotAcceptableException;

    ResponseEntity<FotoProdutoModel> remover(Long id, Long produtoId);

}

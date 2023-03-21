package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.model.input.FotoProdutoInput;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.nio.file.Path;

import static java.util.UUID.randomUUID;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
    @PutMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(@PathVariable("restauranteId") Long id,
                              @PathVariable Long produtoId, @Valid FotoProdutoInput fotoProdutoInput) {
        // criando um nome para o arquivo
        String nomeArquivo = randomUUID() + "_" + fotoProdutoInput.getArquivo().getOriginalFilename();

        // salvando o arquivo no caminho informado
        Path arquivoFoto = Path.of("/Users/eduar/upload/catalogo", nomeArquivo);

        try {
            // transferiando o arquivo recebido pela requisicao para
            // o caminho criado/informado
            fotoProdutoInput.getArquivo().transferTo(arquivoFoto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

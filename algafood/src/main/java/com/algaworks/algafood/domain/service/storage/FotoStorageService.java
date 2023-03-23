package com.algaworks.algafood.domain.service.storage;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;

import static java.util.UUID.randomUUID;

public interface FotoStorageService {
    void armazenar(NovaFoto novaFoto);

    default String gerarNomeArquivo(String nomeOriginal) {
        return randomUUID().toString() + "_" + nomeOriginal;
    }

    @Builder
    @Getter
    class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream; // fluxo de entrada/leitura do arquivo
    }

}

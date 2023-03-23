package com.algaworks.algafood.domain.service.storage;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;

import static java.util.UUID.randomUUID;

public interface FotoStorageService {
    InputStream recuperar(String nomeArquivo);

    void armazenar(NovaFoto novaFoto);

    void remover(String nomeArquivo);

    default String gerarNomeArquivo(String nomeOriginal) {
        return randomUUID() + "_" + nomeOriginal;
    }

    default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
        this.armazenar(novaFoto);

        if (nomeArquivoAntigo != null)
            this.remover(nomeArquivoAntigo);
    }

    @Builder
    @Getter
    class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream; // fluxo de entrada/leitura do arquivo
    }

}

package com.algaworks.algafood.domain.service.storage;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;

public interface FotoStorageService {
    void armazenar(NovaFoto novaFoto);

    @Builder
    @Getter
    class NovaFoto {
        private String nomeArquivo;
        private InputStream inputStream; // fluxo de entrada/leitura do arquivo
    }

}

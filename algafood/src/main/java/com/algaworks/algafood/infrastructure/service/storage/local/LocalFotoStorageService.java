package com.algaworks.algafood.infrastructure.service.storage.local;

import com.algaworks.algafood.core.storage.properties.StorageProperties;
import com.algaworks.algafood.domain.service.storage.FotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.exception.StorageException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.*;
import static org.springframework.util.FileCopyUtils.copy;

public class LocalFotoStorageService implements FotoStorageService {
    @Autowired
    private StorageProperties storageProperties;

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            // caminho onde o arquivo sera armazenado
            Path arquivoPath = getArquivoPath(novaFoto.getNomeArquivo());

            // copiando a foto para o novo caminho
            copy(novaFoto.getInputStream(), newOutputStream(arquivoPath));
        } catch (IOException e) {
            throw new StorageException("Nao foi possivel armazenar arquivo.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {
        try {
            // obtendo o caminho do arquivo com o nome do arquivo
            Path arquivoPath = getArquivoPath(nomeArquivo);

            // caso o arquivo ja exista excluir
            deleteIfExists(arquivoPath);
        } catch (IOException e) {
            throw new StorageException("Nao foi possivel excluir arquivo.", e);
        }
    }

    // metodo que fornece os dados dos arquivos para download eh necessario recuperar
    @Override
    public FotoRecuperada recuperar(String nomeArquivo) {
        try {
            // obtendo o caminho do arquivo com o nome do arquivo
            Path arquivoPath = getArquivoPath(nomeArquivo);

            // recuperando o arquivo
            return FotoRecuperada.builder()
                    .inputStream(newInputStream(arquivoPath))
                    .build();
        } catch (IOException e) {
            throw new StorageException("Nao foi possivel recuperar arquivo.", e);
        }
    }

    private Path getArquivoPath(String nomeArquivo) {
        // caminho da pasta mais o arquivo
        return storageProperties.getLocal().getDiretorioFotos().resolve(Path.of(nomeArquivo));
    }

}

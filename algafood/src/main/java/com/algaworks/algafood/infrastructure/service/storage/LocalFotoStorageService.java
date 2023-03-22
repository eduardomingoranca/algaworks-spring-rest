package com.algaworks.algafood.infrastructure.service.storage;

import com.algaworks.algafood.domain.service.storage.FotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.exception.StorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.newOutputStream;
import static org.springframework.util.FileCopyUtils.copy;

@Service
public class LocalFotoStorageService implements FotoStorageService {
    @Value("${algafood.storage.local.diretorio-fotos}")
    private Path diretorioFotos;

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

    private Path getArquivoPath(String nomeArquivo) {
        // caminho da pasta mais o arquivo
        return diretorioFotos.resolve(Path.of(nomeArquivo));
    }

}

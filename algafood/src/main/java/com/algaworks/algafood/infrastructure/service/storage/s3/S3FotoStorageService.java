package com.algaworks.algafood.infrastructure.service.storage.s3;

import com.algaworks.algafood.core.storage.properties.StorageProperties;
import com.algaworks.algafood.domain.service.storage.FotoStorageService;
import com.algaworks.algafood.infrastructure.service.storage.exception.StorageException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

import static com.amazonaws.services.s3.model.CannedAccessControlList.PublicRead;
import static java.lang.String.format;

@Service
public class S3FotoStorageService implements FotoStorageService {
    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private StorageProperties storageProperties;

    @Override
    public InputStream recuperar(String nomeArquivo) {
        return null;
    }

    @Override
    public void armazenar(NovaFoto novaFoto) {
        try {
            String caminhoArquivo = getCaminhoArquivo(novaFoto.getNomeArquivo());

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(novaFoto.getContentType());

            // criando o objeto de requisicao
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    storageProperties.getS3().getBucket(),
                    caminhoArquivo,
                    novaFoto.getInputStream(),
                    objectMetadata)
                    // adicionando o acesso publico de leitura
                    .withCannedAcl(PublicRead);

            // adicionando o objeto no bucket
            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new StorageException("Nao foi possivel enviar arquivo para Amazon S3.", e);
        }
    }

    @Override
    public void remover(String nomeArquivo) {

    }

    private String getCaminhoArquivo(String nomeArquivo) {
        return format("%s/%s", storageProperties.getS3().getDiretorioFotos(), nomeArquivo);
    }

}

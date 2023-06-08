package com.algaworks.algafood.api.v1.model.input;

import com.algaworks.algafood.core.validation.annotation.FileContentType;
import com.algaworks.algafood.core.validation.annotation.FileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@Getter
@Setter
public class FotoProdutoInput {
    @NotNull
    @FileSize(max = "500KB")
    @FileContentType(allowed = { IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE })
    private MultipartFile arquivo;

    @NotBlank
    private String descricao;

}

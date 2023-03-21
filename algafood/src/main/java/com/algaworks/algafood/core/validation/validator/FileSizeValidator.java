package com.algaworks.algafood.core.validation.validator;

import com.algaworks.algafood.core.validation.annotation.FileSize;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static org.springframework.util.unit.DataSize.parse;

public class FileSizeValidator implements ConstraintValidator<FileSize, MultipartFile> {

    // classe spring que representa um tamanho de dado
    // atraves dessa classe podemos fazer o parse da spring para bytes
    private DataSize maxSize;

    //    inicializa o validador/instancia para preparar para chamadas futuras do methodo isValid
    @Override
    public void initialize(FileSize constraintAnnotation) {
        this.maxSize = parse(constraintAnnotation.max());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return multipartFile == null || multipartFile.getSize() <= this.maxSize.toBytes();
    }

}

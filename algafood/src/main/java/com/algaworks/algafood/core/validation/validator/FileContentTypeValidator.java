package com.algaworks.algafood.core.validation.validator;

import com.algaworks.algafood.core.validation.annotation.FileContentType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static java.util.Arrays.asList;

public class FileContentTypeValidator implements ConstraintValidator<FileContentType, MultipartFile> {
    private List<String> allowedContentTypes;

    @Override
    public void initialize(FileContentType constraintAnnotation) {
        this.allowedContentTypes = asList(constraintAnnotation.allowed());
    }

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext constraintValidatorContext) {
        return multipartFile == null || this.allowedContentTypes.contains(multipartFile.getContentType());
    }

}

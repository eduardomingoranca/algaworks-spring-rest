package com.algaworks.algafood.core.validation.annotation;

import com.algaworks.algafood.core.validation.validator.FileSizeValidator;
import com.algaworks.algafood.core.validation.validator.MultiploValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = { FileSizeValidator.class })
public @interface FileSize {

    String message() default "tamanho do arquivo invalido";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String max();
}

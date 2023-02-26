package com.algaworks.algafood.core.validation.validator;

import com.algaworks.algafood.core.validation.annotation.ValorZeroIncluiDescricao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ValidationException;
import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.util.Objects.requireNonNull;
import static org.springframework.beans.BeanUtils.getPropertyDescriptor;

public class ValorZeroIncluiDescricaoValidator implements ConstraintValidator<ValorZeroIncluiDescricao, Object> {
    private String valorField;
    private String descricaoField;
    private String descricaoObrigatoria;

    @Override
    public void initialize(ValorZeroIncluiDescricao constraintAnnotation) {
        this.valorField = constraintAnnotation.valorField();
        this.descricaoField = constraintAnnotation.descricaoField();
        this.descricaoObrigatoria = constraintAnnotation.descricaoObrigatoria();
    }

    @Override
    public boolean isValid(Object objetoValidacao, ConstraintValidatorContext constraintValidatorContext) {
        boolean valido = true;

        try {
            BigDecimal valor = (BigDecimal) requireNonNull(getPropertyDescriptor(objetoValidacao.getClass(), valorField))
                    .getReadMethod().invoke(objetoValidacao);

            String descricao = (String) requireNonNull(getPropertyDescriptor(objetoValidacao.getClass(), descricaoField))
                    .getReadMethod().invoke(objetoValidacao);

            if (valor != null && ZERO.compareTo(valor) == 0 && descricao != null)
                valido = descricao.toLowerCase().contains(this.descricaoObrigatoria.toLowerCase());

            return valido;
        } catch (Exception e) {
            throw new ValidationException();
        }

    }
}

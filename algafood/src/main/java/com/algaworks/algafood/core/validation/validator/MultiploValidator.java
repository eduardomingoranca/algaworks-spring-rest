package com.algaworks.algafood.core.validation.validator;

import com.algaworks.algafood.core.validation.annotation.Multiplo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

public class MultiploValidator implements ConstraintValidator<Multiplo, Number> {

    private int numeroMultiplo;

//    inicializa o validador/instancia para preparar para chamadas futuras do methodo isValid
    @Override
    public void initialize(Multiplo constraintAnnotation) {
        this.numeroMultiplo = constraintAnnotation.numero();
    }

//    implementando a logica de validacao.
    @Override
    public boolean isValid(Number value, ConstraintValidatorContext constraintValidatorContext) {
        boolean valido = true;

        if (value != null) {
            var valorDecimal = BigDecimal.valueOf(value.doubleValue());
            var multiploDecimal =  BigDecimal.valueOf(this.numeroMultiplo);
            var resto = valorDecimal.remainder(multiploDecimal);

//            se eles for iguais retorna zero, caso retorne zero o numero eh multiplo.
            valido = ZERO.compareTo(resto) == 0;
        }

        return valido;
    }

}

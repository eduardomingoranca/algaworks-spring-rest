package com.algaworks.algafood.core.validation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Getter
public class ValidacaoException extends RuntimeException {

//        BindingResult -> armazena as violacoes de constraints de validacao, ou seja,
//        dentro dessa classe pode-se ter o acesso a quais fields/propriedades foram violadas
    private BindingResult bindingResult;

}

package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.status;

// @ControllerAdvice -> anotacao informa que dentro deste componente pode-se adicionar
// as exception handler que as exceptions que todos os controllers do projeto serao tratadas por aqui.
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    // metodo responsavel por tratar e capturar exceptions dos controllers
    // ExceptionHandler -> informa a classe e excecao que tera o tratamento e o retorno.
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> tratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e) {
        Problema problema = Problema.builder()
                .dataHora(LocalDateTime.now())
                .mensagem(e.getMessage())
                .build();

        return status(NOT_FOUND).body(problema);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> tratarNegocioException(NegocioException e) {
        Problema problema = Problema.builder()
                .dataHora(LocalDateTime.now())
                .mensagem(e.getMessage())
                .build();

        return status(BAD_REQUEST).body(problema);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> tratarEntidadeEmUsoException(EntidadeEmUsoException e) {
        Problema problema = Problema.builder()
                .dataHora(LocalDateTime.now())
                .mensagem(e.getMessage())
                .build();

        return status(CONFLICT).body(problema);
    }

}

package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static java.time.LocalDateTime.now;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> tratarEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e,
                                                                       WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), NOT_FOUND, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> tratarNegocioException(NegocioException e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), BAD_REQUEST, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> tratarEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {
        return handleExceptionInternal(e, e.getMessage(), new HttpHeaders(), CONFLICT, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        if (body == null) {
            body = Problema.builder()
                    .dataHora(now())
                    .mensagem(status.getReasonPhrase()) // ReasonPhrase -> descreve o status que esta sendo retornado
                    .build();
        } else if (body instanceof String) {
            body = Problema.builder()
                    .dataHora(now())
                    .mensagem((String) body)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

}

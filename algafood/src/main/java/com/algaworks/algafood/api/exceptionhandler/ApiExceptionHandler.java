package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

import static com.algaworks.algafood.api.exceptionhandler.ProblemType.*;
import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.exception.ExceptionUtils.getRootCause;
import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String MSG_ERRO_GENERICA_USUARIO_FINAL = "Ocorreu um erro interno inesperado no sistema. " +
            "Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.";

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        // retornando a causa raiz da exception
        Throwable rootCause = getRootCause(ex);

        if (rootCause instanceof InvalidFormatException)
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        else if (rootCause instanceof PropertyBindingException)
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        else if (rootCause instanceof TypeMismatchException)
            return handleTypeMismatch((TypeMismatchException) rootCause, headers, status, request);
        else if (rootCause instanceof NoHandlerFoundException)
            return handleNoHandlerFoundException((NoHandlerFoundException) rootCause, headers, status, request);

        String detail = "O corpo da requisicao esta invalido. Verifique erro de sintaxe.";

        Problem problem = createProblemBuilder(status, MENSAGEM_INCOMPREENSIVEL, detail,
                MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
                                                                HttpStatus status, WebRequest request) {

        String path = ex.getPath().stream()
//                .map(reference -> reference.getFieldName())
                .map(JsonMappingException.Reference::getFieldName)
                .collect(joining("."));

        String detail = format("A propriedade '%s' recebeu o valor '%s', " +
                        "que eh de um tipo invalido. Corrija e informe um valor compativel com o tipo %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        Problem problem = createProblemBuilder(status, MENSAGEM_INCOMPREENSIVEL, detail,
                MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        // obtendo o campo informado erronemente
        String path = ex.getPath().stream()
                .map(JsonMappingException.Reference::getFieldName)
                .collect(joining("."));

        String detail = format("A propriedade %s do tipo %s esta invalida. " +
                        "Por favor corrija e informe uma propriedade valida.",
                path, ex.getReferringClass().getSimpleName());

        Problem problem = createProblemBuilder(status, MENSAGEM_INCOMPREENSIVEL, detail,
                MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatus status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            String path = ((MethodArgumentTypeMismatchException) ex).getName();

            String detail = format("O parametro de URL %s recebeu o valor %s, que eh de um tipo invalido. " +
                            "Corrija e informe um valor compativel com o tipo %s.",
                    path, ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());

            Problem problem = createProblemBuilder(status, PARAMETRO_INVALIDO, detail,
                    MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .build();

            return handleExceptionInternal(ex, problem, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatus status, WebRequest request) {
        String path = ex.getRequestURL();

        String detail = format("O recurso %s, que voce tentou acessar, eh inexistente.",
                path);

        Problem problem = createProblemBuilder(status, RECURSO_NAO_ENCONTRADO, detail,
                MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return this.handleExceptionInternal(ex, problem, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        String detail = "Um ou mais campos estao invalidos. Faca o preenchimento correto e " +
                "tente novamente.";

        Problem problem = createProblemBuilder(status, DADOS_INVALIDOS, detail, detail)
                .build();

        return this.handleExceptionInternal(ex, problem, headers, status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadeNaoEncontradaException(EntidadeNaoEncontradaException e,
                                                                       WebRequest request) {
        HttpStatus status = NOT_FOUND;
//        ProblemType type = ENTIDADE_NAO_ENCONTRADA;
        String detail = e.getMessage();

        Problem problem = createProblemBuilder(status, RECURSO_NAO_ENCONTRADO, detail, detail)
                .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<Object> handleNegocioException(NegocioException e, WebRequest request) {
        HttpStatus status = BAD_REQUEST;
        String detail = e.getMessage();

        Problem problem = createProblemBuilder(status, ERRO_NEGOCIO, detail,
                MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<Object> handleEntidadeEmUsoException(EntidadeEmUsoException e, WebRequest request) {
        HttpStatus status = CONFLICT;
        String detail = e.getMessage();

        Problem problem = createProblemBuilder(status, ENTIDADE_EM_USO, detail, detail)
                .build();

        return handleExceptionInternal(e, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleOtherException(Exception ex, WebRequest request) {
        HttpStatus status = INTERNAL_SERVER_ERROR;

        Problem problem = createProblemBuilder(status, ERRO_DE_SISTEMA, MSG_ERRO_GENERICA_USUARIO_FINAL,
                MSG_ERRO_GENERICA_USUARIO_FINAL)
                .build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatus status, WebRequest request) {

        if (body == null) {
            body = Problem.builder()
                    .title(status.getReasonPhrase()) // ReasonPhrase -> descreve o status que esta sendo retornado
                    .status(status.value())
                    .timestamp(now())
                    .userMessage(MSG_ERRO_GENERICA_USUARIO_FINAL)
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .title((String) body)
                    .status(status.value())
                    .timestamp(now())
                    .userMessage(body.toString())
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    // criando um builder
    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType,
                                                        String detail, String userMessage) {
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .userMessage(userMessage)
                .timestamp(now())
                .detail(detail);
    }

}

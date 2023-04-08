package com.algaworks.algafood.client.algafood_java_client.client.api.exception;

import com.algaworks.algafood.client.algafood_java_client.client.model.Problem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestClientResponseException;

import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

@Slf4j
public class ClientAPIException extends RuntimeException {
    @Getter
    private Problem problem;

    public ClientAPIException(String message, RestClientResponseException cause) {
        super(message, cause);
        deserializeProblem(cause);
    }

    private void deserializeProblem(RestClientResponseException cause) {
        // classe responsavel por serializar e deserializar
        // STRING TO OBJECT (DESERIALIZE)
        // OBJECT TO STRING (SERIALIZE)
        ObjectMapper mapper = new ObjectMapper();
        // nao falhar caso tenha propriedades na string e nao existe no object
        mapper.configure(FAIL_ON_UNKNOWN_PROPERTIES, false);
        // adicionando o modulo para deserialize atributos do tipo javatime
        mapper.registerModule(new JavaTimeModule());
        mapper.findAndRegisterModules();

        try {
            this.problem = mapper.readValue(cause.getResponseBodyAsString(), Problem.class);
        } catch (JsonProcessingException e) {
            log.warn("Nao foi possivel desserializar a resposta em um problema", e);
        }
    }

}

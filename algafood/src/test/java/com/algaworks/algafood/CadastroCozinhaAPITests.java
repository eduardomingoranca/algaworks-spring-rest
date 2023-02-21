package com.algaworks.algafood;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.springframework.http.HttpStatus.OK;

// subindo um servidor web
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaAPITests {

    // injetando o numero da porta aleatoria
    @LocalServerPort
    private int port;

    @Test
    void deveRetornarStatus200QuandoConsultarCozinhas() {
        // verificando a resposta quando ocorre o erro no teste
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        // Rest Assured -> biblioteca para teste e validacao de rest apis
        // dado que o caminho /cozinhas com a porta 8080 aceitando um retorno em json
            given()
                .basePath("/cozinhas")
                .port(port)
                .accept(JSON)
        // quando chamar o metodo get
           .when()
                .get()
        // entao o status que deve retornar eh o 200
           .then()
                .statusCode(OK.value());
    }

}

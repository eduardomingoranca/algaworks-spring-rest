package com.algaworks.algafood;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

// subindo um servidor web
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CadastroCozinhaAPITests {

    // injetando o numero da porta aleatoria
    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        // habilitatando os logs e verificando a resposta quando ocorre o erro no teste
        enableLoggingOfRequestAndResponseIfValidationFails();
        // Rest Assured -> biblioteca para teste e validacao de rest apis
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";
    }

    @Test
    void deveRetornarStatus200QuandoConsultarCozinhas() {
        // dado que o caminho /cozinhas com a porta 8080 aceitando um retorno em json
        given()
                .accept(JSON)
                // quando chamar o metodo get
        .when()
                .get()
                // entao o status que deve retornar eh o 200
        .then()
                .statusCode(OK.value());
    }

    @Test
    void deveConterQuatroCozinhasQuandoConsultarCozinhas() {
        given()
                .accept(JSON)
        .when()
                .get()
        .then()
                // hamcrest -> biblioteca para escrever expressoes
                // com regras de correspondencia entre objetos
                .body("", hasSize(4))
                .body("nome", hasItems("Indiana", "Tailandesa"));
    }

    @Test
    void deveRetornarStatus201QuandoCadastrarCozinha() {
        given()
                .body("{ \"nome\": \"Chinesa\" }")
                .contentType(JSON)
                .accept(JSON)
        .when()
                .post()
        .then()
                .statusCode(CREATED.value());
    }

}

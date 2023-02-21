package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import static com.algaworks.algafood.util.ResourceUtils.getContentFromResource;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

// subindo um servidor web
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// configurando para utilizar o arquivo de propriedades application-test.properties
@TestPropertySource("/application-test.properties")
class CadastroCozinhaAPITests {
    public static final int COZINHA_ID_INEXISTENTE = 100;

    // injetando o numero da porta aleatoria
    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private int quantidadeCozinha;

    private Cozinha cozinhaDois;

    @BeforeEach
    void setUp() {
        // habilitatando os logs e verificando a resposta quando ocorre o erro no teste
        enableLoggingOfRequestAndResponseIfValidationFails();
        // Rest Assured -> biblioteca para teste e validacao de rest apis
        RestAssured.port = port;
        RestAssured.basePath = "/cozinhas";

        // limpando os dados de todas as tabelas do banco de dados
        databaseCleaner.clearTables();
        prepararDados();
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
    void deveConterCozinhasQuandoRealizarConsulta() {
        given()
                .accept(JSON)
        .when()
                .get()
        .then()
                // hamcrest -> biblioteca para escrever expressoes
                // com regras de correspondencia entre objetos
                .body("", hasSize(quantidadeCozinha))
                .body("nome", hasItems("Americana", "Tailandesa"));
    }

    @Test
    void deveRetornarStatus201QuandoCadastrarCozinha() {
        given()
                .body(getContentFromResource("/adicionarCozinha.json"))
                .contentType(JSON)
                .accept(JSON)
        .when()
                .post()
        .then()
                .statusCode(CREATED.value());
    }

    // GET /cozinhas/{cozinhaId}
    @Test
    void deveRetornarRespostaEStatusCorretosQuandoConsultarCozinhaExistente() {
        given()
                .pathParam("cozinhaId", cozinhaDois.getId())
                .accept(JSON)
        .when()
                .get("/{cozinhaId}")
        .then()
                .statusCode(OK.value())
                .body("nome", equalTo(cozinhaDois.getNome()));
    }

    @Test
    void deveRetornarStatus404QuandoConsultarCozinhaInexistente() {
        given()
                .pathParam("cozinhaId", COZINHA_ID_INEXISTENTE)
                .accept(JSON)
        .when()
                .get("/{cozinhaId}")
        .then()
                .statusCode(NOT_FOUND.value());
    }

    // responsavel por inserir massa de dados
    private void prepararDados() {
        Cozinha cozinhaUm = new Cozinha();
        cozinhaUm.setNome("Tailandesa");
        cozinhaRepository.save(cozinhaUm);

        cozinhaDois = new Cozinha();
        cozinhaDois.setNome("Americana");
        cozinhaRepository.save(cozinhaDois);

        quantidadeCozinha = (int) cozinhaRepository.count();
    }

}

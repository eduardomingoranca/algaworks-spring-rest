package com.algaworks.algafood;

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.util.DatabaseCleaner;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.time.OffsetDateTime;

import static com.algaworks.algafood.util.ResourceUtils.getContentFromResource;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.math.BigDecimal.valueOf;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpStatus.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application-test.properties")
public class CadastroRestauranteAPITests {
    public static final int RESTAURANTE_ID_INEXISTENTE = 100;

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    private Restaurante restauranteUm;

    private Restaurante restauranteDois;

    @BeforeEach
    void setUp() {
        enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = port;
        RestAssured.basePath = "/restaurantes";

        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    void deveRetornarStatus200QuandoConsultarResturantes() {
        given()
                .accept(JSON)
        .when()
                .get()
        .then()
                .statusCode(OK.value());
    }

    @Test
    void deveRetornarRespostaEStatus200QuandoConsultarRestauranteExistente() {
        given()
                .pathParam("restauranteId", restauranteUm.getId())
                .accept(JSON)
        .when()
                .get("/{restauranteId}")
        .then()
                .statusCode(OK.value())
                .body("nome", equalTo(restauranteUm.getNome()));
    }

    @Test
    void deveRetornarStatus404QuandoConsultarRestauranteInexistente() {
        given()
                .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
                .accept(JSON)
        .when()
                .get("/{restauranteId}")
        .then()
                .statusCode(NOT_FOUND.value());
    }

    @Test
    void deveRetornarRespostaEStatus200QuandoAtualizarRestauranteExistente() {
        given()
                .pathParam("restauranteId", restauranteDois.getId())
                .body(getContentFromResource("/json/atualizarRestauranteTailandes.json"))
                .contentType(JSON)
                .accept(JSON)
        .when()
                .put("/{restauranteId}")
        .then()
                .statusCode(OK.value());
    }

    @Test
    void deveRetornarStatus404QuandoAtualizarRestauranteInexistente() {
        given()
                .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
                .body(getContentFromResource("/json/atualizarRestauranteTailandes.json"))
                .contentType(JSON)
                .accept(JSON)
        .when()
                .put("/{restauranteId}")
        .then()
                .statusCode(NOT_FOUND.value());
    }

    @Test
    void deveRetornarStatus201QuandoCadastrarRestaurante() {
        given()
                .body(getContentFromResource("/json/adicionarRestauranteIndiano.json"))
                .contentType(JSON)
                .accept(JSON)
        .when()
                .post()
        .then()
                .statusCode(CREATED.value());
    }

    private void prepararDados() {
        Cozinha cozinhaUm = new Cozinha();
        cozinhaUm.setNome("Tailandesa");
        cozinhaRepository.save(cozinhaUm);

        Cozinha cozinhaDois = new Cozinha();
        cozinhaDois.setNome("Indiana");
        cozinhaRepository.save(cozinhaDois);

        restauranteUm = new Restaurante();
        restauranteUm.setNome("Thai Delivery");
        restauranteUm.setTaxaFrete(valueOf(9.50));
        restauranteUm.setCozinha(cozinhaUm);
        restauranteUm.setDataCadastro(OffsetDateTime.now());
        restauranteUm.setDataAtualizacao(OffsetDateTime.now());
        restauranteRepository.save(restauranteUm);

        restauranteDois = new Restaurante();
        restauranteDois.setNome("Tuk Tuk Comida Indiana");
        restauranteDois.setTaxaFrete(valueOf(15));
        restauranteDois.setCozinha(cozinhaDois);
        restauranteDois.setDataCadastro(OffsetDateTime.now());
        restauranteDois.setDataAtualizacao(OffsetDateTime.now());
        restauranteRepository.save(restauranteDois);
    }

}

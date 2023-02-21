package com.algaworks.algafood;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


// @SpringBootTest -> fornece as funcionalidades do spring nos testes
@SpringBootTest
class CadastroCozinhaIT {

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @Test
    void deveAtribuirIDQuandoCadastrarCozinhaComDadosCorretos() {
        // cenario
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome("Chinesa");

        // acao
        novaCozinha = cadastroCozinha.salvar(novaCozinha);

        // validacao
        assertThat(novaCozinha).isNotNull();
        assertThat(novaCozinha.getId()).isNotNull();
    }

    @Test
    void deveFalharQuandoCadastrarCozinhaSemNome() {
        Cozinha novaCozinha = new Cozinha();
        novaCozinha.setNome(null);

        ConstraintViolationException erroEsperado =
                assertThrows(ConstraintViolationException.class, () ->
                        cadastroCozinha.salvar(novaCozinha));

        assertThat(erroEsperado).isNotNull();
    }

    @Test
    void deveFalharQuandoExcluirCozinhaEmUso() {
        EntidadeEmUsoException erroEsperado =
                assertThrows(EntidadeEmUsoException.class, () ->
                        cadastroCozinha.excluir(1L));

        assertThat(erroEsperado).isNotNull();
    }

    @Test
    void deveFalharQuandoExcluirCozinhaInexistente() {
        CozinhaNaoEncontradaException erroEsperado =
                assertThrows(CozinhaNaoEncontradaException.class, () ->
                        cadastroCozinha.excluir(100L));

        assertThat(erroEsperado).isNotNull();
    }

}

package com.algaworks.algafood;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;

// @SpringBootTest -> fornece as funcionalidades do spring nos testes
@SpringBootTest
class AlgafoodApplicationTests {

	@Test
	void contextLoads() {
		assertFalse(true);
	}

}

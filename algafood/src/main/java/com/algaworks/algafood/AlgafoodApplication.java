package com.algaworks.algafood;

import com.algaworks.algafood.infrastructure.repository.CustomJPARepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static java.util.TimeZone.getTimeZone;
import static java.util.TimeZone.setDefault;

@SpringBootApplication
// ativando o repositorio customizado
@EnableJpaRepositories(repositoryBaseClass = CustomJPARepositoryImpl.class)
public class AlgafoodApplication {

	public static void main(String[] args) {
		// utilizando o timezone utc
		setDefault(getTimeZone("UTC"));
		SpringApplication.run(AlgafoodApplication.class, args);
	}

}

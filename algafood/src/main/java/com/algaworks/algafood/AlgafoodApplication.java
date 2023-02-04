package com.algaworks.algafood;

import com.algaworks.algafood.infrastructure.repository.CustomJPARepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
// ativando o repositorio customizado
@EnableJpaRepositories(repositoryBaseClass = CustomJPARepositoryImpl.class)
public class AlgafoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgafoodApplication.class, args);
	}

}

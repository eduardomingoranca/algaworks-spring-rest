package com.algaworks.algafood.core.mail;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Validated
@Getter
@Setter
@Configuration
@ConfigurationProperties("algafood.email")
public class EmailProperties {
    @NotNull
    private String remetente;

}

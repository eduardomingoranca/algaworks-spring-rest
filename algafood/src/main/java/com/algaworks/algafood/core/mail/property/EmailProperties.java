package com.algaworks.algafood.core.mail.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

import static com.algaworks.algafood.core.mail.property.EmailProperties.Implementacao.FAKE;

@Validated
@Getter
@Setter
@Configuration
@ConfigurationProperties("algafood.email")
public class EmailProperties {
    // Atribuimos FAKE como padrao
    // Isso evita o problema de enviar emails de verdade caso esqueca
    // de definir a propriedade
    private Implementacao impl = FAKE;

    @NotNull
    private String remetente;

    public enum Implementacao {
        SMTP, FAKE
    }

}

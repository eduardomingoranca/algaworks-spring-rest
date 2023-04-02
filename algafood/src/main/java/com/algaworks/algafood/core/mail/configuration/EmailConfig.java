package com.algaworks.algafood.core.mail.configuration;

import com.algaworks.algafood.core.mail.property.EmailProperties;
import com.algaworks.algafood.domain.service.mail.EnvioEmailService;
import com.algaworks.algafood.infrastructure.service.mail.fake.FakeEnvioEmailService;
import com.algaworks.algafood.infrastructure.service.mail.smtp.SMTPEnvioEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {
    @Autowired
    private EmailProperties emailProperties;

    @Bean
    public EnvioEmailService envioEmailService() {
        return switch (emailProperties.getImpl()) {
            case FAKE -> new FakeEnvioEmailService();
            case SMTP -> new SMTPEnvioEmailService();
        };
    }

}

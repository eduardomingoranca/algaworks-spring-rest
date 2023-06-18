package com.algaworks.algafood.infrastructure.service.mail.sandbox;

import com.algaworks.algafood.core.mail.property.EmailProperties;
import com.algaworks.algafood.infrastructure.service.mail.smtp.SMTPEnvioEmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;


public class SandboxEnvioEmailService extends SMTPEnvioEmailService {
    @Autowired
    private EmailProperties emailProperties;

    // Separei a criacao de MimeMessage em um metodo na classe pai (criarMimeMessage),
    // para possibilitar a sobrescrita desse metodo aqui
    @Override
    protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
        MimeMessage mimeMessage = super.criarMimeMessage(mensagem);

        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setTo(emailProperties.getSandbox().getDestinatario());

        return mimeMessage;
    }

}

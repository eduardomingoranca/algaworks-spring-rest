package com.algaworks.algafood.infrastructure.service.mail.smtp;

import com.algaworks.algafood.core.mail.property.EmailProperties;
import com.algaworks.algafood.domain.service.mail.EnvioEmailService;
import com.algaworks.algafood.infrastructure.service.mail.exception.EmailException;
import com.algaworks.algafood.infrastructure.service.mail.template.ProcessadorEmailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

public class SMTPEnvioEmailService implements EnvioEmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private ProcessadorEmailTemplate processadorEmailTemplate;

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            // criando a mensagem que sera envia pelo email
            MimeMessage mimeMessage = criarMimeMessage(mensagem);

            // enviando o email
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Nao foi possivel enviar email", e);
        }
    }

    protected MimeMessage criarMimeMessage(Mensagem mensagem) throws MessagingException {
        String corpo = processadorEmailTemplate.processarTemplate(mensagem);

        // mensagem que sera enviada por email
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        // construindo o email
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
        helper.setFrom(emailProperties.getRemetente());
        helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
        helper.setSubject(mensagem.getAssunto());
        helper.setText(corpo, true);

        return mimeMessage;
    }

}

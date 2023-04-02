package com.algaworks.algafood.infrastructure.service.mail;

import com.algaworks.algafood.core.mail.EmailProperties;
import com.algaworks.algafood.domain.service.mail.EnvioEmailService;
import com.algaworks.algafood.infrastructure.service.mail.exception.EmailException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
public class SMTPEnvioEmailService implements EnvioEmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            // mensagem que sera enviada por email
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            // construindo o email
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(emailProperties.getRemetente());
            helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            helper.setSubject(mensagem.getAssunto());
            helper.setText(mensagem.getCorpo(), true);

            // enviando o email
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Nao foi possivel enviar email.", e);
        }
    }

}

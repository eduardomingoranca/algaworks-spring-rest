package com.algaworks.algafood.infrastructure.service.mail.smtp;

import com.algaworks.algafood.core.mail.property.EmailProperties;
import com.algaworks.algafood.domain.service.mail.EnvioEmailService;
import com.algaworks.algafood.infrastructure.service.mail.exception.EmailException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

import static org.springframework.ui.freemarker.FreeMarkerTemplateUtils.processTemplateIntoString;

public class SMTPEnvioEmailService implements EnvioEmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private EmailProperties emailProperties;

    @Autowired
    private Configuration freemarkerConfig;

    @Override
    public void enviar(Mensagem mensagem) {
        try {
            String corpo = processarTemplate(mensagem);

            // mensagem que sera enviada por email
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            // construindo o email
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(emailProperties.getRemetente());
            helper.setTo(mensagem.getDestinatarios().toArray(new String[0]));
            helper.setSubject(mensagem.getAssunto());
            helper.setText(corpo, true);

            // enviando o email
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            throw new EmailException("Nao foi possivel enviar email.", e);
        }
    }

    protected String processarTemplate(Mensagem mensagem) {
        try {
            // obtendo o nome do arquivo do template
            Template template = freemarkerConfig.getTemplate(mensagem.getCorpo());

            // obtem o html precessar o arquivo utilizando os atributos do objeto java
            // e retorna em string output
            return processTemplateIntoString(template, mensagem.getVariaveis());
        } catch (Exception e) {
            throw new EmailException("Nao foi possivel montar o template do email.", e);
        }
    }

}

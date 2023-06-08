package com.algaworks.algafood.infrastructure.service.mail.template;

import com.algaworks.algafood.domain.service.mail.EnvioEmailService;
import com.algaworks.algafood.infrastructure.service.mail.exception.EmailException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.springframework.ui.freemarker.FreeMarkerTemplateUtils.processTemplateIntoString;

@Component
public class ProcessadorEmailTemplate {

    @Autowired
    private Configuration freemarkerConfig;

    public String processarTemplate(EnvioEmailService.Mensagem mensagem) {
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

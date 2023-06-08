package com.algaworks.algafood.infrastructure.service.mail.fake;

import com.algaworks.algafood.domain.service.mail.EnvioEmailService;
import com.algaworks.algafood.infrastructure.service.mail.template.ProcessadorEmailTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class FakeEnvioEmailService implements EnvioEmailService {
    @Autowired
    private ProcessadorEmailTemplate processadorEmailTemplate;

    @Override
    public void enviar(Mensagem mensagem) {
        // Foi necessario alterar o modificador de acesso do metodo processarTemplate
        // da classe pai para "protected", para poder chamar aqui
        String corpo = processadorEmailTemplate.processarTemplate(mensagem);

        log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
    }

}

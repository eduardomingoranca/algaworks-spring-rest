package com.algaworks.algafood.infrastructure.service.mail.fake;

import com.algaworks.algafood.infrastructure.service.mail.smtp.SMTPEnvioEmailService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeEnvioEmailService extends SMTPEnvioEmailService {
    @Override
    public void enviar(Mensagem mensagem) {
        // Foi necessario alterar o modificador de acesso do metodo processarTemplate
        // da classe pai para "protected", para poder chamar aqui
        String corpo = processarTemplate(mensagem);

        log.info("[FAKE E-MAIL] Para: {}\n{}", mensagem.getDestinatarios(), corpo);
    }

}

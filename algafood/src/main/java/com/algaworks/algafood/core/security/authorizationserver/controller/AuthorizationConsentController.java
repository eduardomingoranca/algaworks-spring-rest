package com.algaworks.algafood.core.security.authorizationserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.HashSet;
import java.util.Set;

import static java.lang.String.format;
import static java.util.Collections.emptySet;
import static org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames.*;
import static org.springframework.util.StringUtils.delimitedListToStringArray;


@Controller
@RequiredArgsConstructor
public class AuthorizationConsentController {

    private final RegisteredClientRepository registeredClientRepository;
    private final OAuth2AuthorizationConsentService consentService;

    @GetMapping("/oauth2/consent")
    public String consent(Principal principal,
                          Model model,
                          @RequestParam(CLIENT_ID) String clientId,
                          @RequestParam(SCOPE) String scope,
                          @RequestParam(STATE) String state) {
        RegisteredClient client = this.registeredClientRepository.findByClientId(clientId);

        if (client == null)
            throw new AccessDeniedException(format("Cliente de %s nao foi encontrado.", clientId));

        OAuth2AuthorizationConsent consent = this.consentService.findById(client.getClientId(), principal.getName());

        String[] scopeArray = delimitedListToStringArray(scope, " ");
        Set<String> scopesParaAprovar = new HashSet<>(Set.of(scopeArray));

        Set<String> scopesAprovadosAnteriormente;
        if (consent != null) {
            scopesAprovadosAnteriormente = consent.getScopes();
            scopesParaAprovar.removeAll(scopesAprovadosAnteriormente);
        } else scopesAprovadosAnteriormente = emptySet();

        model.addAttribute("clientId", clientId);
        model.addAttribute("state", state);
        model.addAttribute("principalName", principal.getName());
        model.addAttribute("scopesParaAprovar", scopesParaAprovar);
        model.addAttribute("scopesAprovadosAnteriormente", scopesAprovadosAnteriormente);

        return "pages/approval";
    }

}

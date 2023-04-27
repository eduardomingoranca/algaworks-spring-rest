package com.algaworks.algafood.api.utils;

import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

import static java.util.Objects.requireNonNull;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.web.context.request.RequestContextHolder.getRequestAttributes;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequestUri;

// anotacao para informar que essa eh um classe utilitaria
@UtilityClass
public class ResourceURIHelper {

    public static void addURIInResponseHeader(Object resourceID) {
        // criando uma URI usando as informacoes da requisicao atual
        URI uri = fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(resourceID)
                .toUri();

        // adicionando a URI no cabecalho (header) location
        HttpServletResponse response = ((ServletRequestAttributes)
                requireNonNull(getRequestAttributes()))
                .getResponse();

        assert response != null;
        response.setHeader(LOCATION, uri.toString());
    }

}

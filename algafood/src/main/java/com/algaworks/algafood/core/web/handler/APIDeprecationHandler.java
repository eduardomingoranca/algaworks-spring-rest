package com.algaworks.algafood.core.web.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class APIDeprecationHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getRequestURI().startsWith("/v1/")) {
            response.addHeader("X-AlgaFood-Deprecated",
                    "Essa versao da API esta depreciada e deixara de existir a partir de 01/06/2023. "
                            + "Use a versao mais atual da API.");
        }

        return true;
    }

}

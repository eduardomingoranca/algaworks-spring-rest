package com.algaworks.algafood.core.web.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import static org.springframework.http.HttpStatus.GONE;


@Component
public class APIRetirementHandler implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getRequestURI().startsWith("/v1/")) {
            response.setStatus(GONE.value());
            return false;
        }

        return true;
    }

}

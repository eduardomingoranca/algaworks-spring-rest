package com.algaworks.algafood.core.web;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

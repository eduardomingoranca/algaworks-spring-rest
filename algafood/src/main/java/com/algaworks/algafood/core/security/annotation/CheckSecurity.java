package com.algaworks.algafood.core.security.annotation;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface CheckSecurity {

    @interface Cozinhas {
        // sera armazenada na classe que foi utilizada apos a compilacao para
        // que seja lida em tempo de execucao.
        @Retention(RUNTIME)
        // anotacao permitida apenas em metodos
        @Target(METHOD)
        @PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
        @interface PodeEditar { }

        @PreAuthorize("isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }

    }

}

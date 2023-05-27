package com.algaworks.algafood.auth.core.userdetails;

import com.algaworks.algafood.auth.domain.model.Usuario;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import static java.util.Collections.emptyList;

@Getter
public class AuthUser extends User {
    private final Long userId;
    private final String fullName;

    public AuthUser(Usuario usuario) {
        super(usuario.getEmail(), usuario.getSenha(), emptyList());

        this.userId = usuario.getId();
        this.fullName = usuario.getNome();
    }

}

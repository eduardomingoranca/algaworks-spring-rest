package com.algaworks.algafood.domain.model;

import javax.persistence.*;
import java.util.Objects;

import static java.util.Objects.hash;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "cozinha")
public class Cozinha {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cozinha cozinha = (Cozinha) o;
        return Objects.equals(id, cozinha.id);
    }

    @Override
    public int hashCode() {
        return hash(id);
    }
}

package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.Groups;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.bytebuddy.pool.TypePool;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

//    @NotNull
//    @NotEmpty // nao aceita valor vazio
    @NotBlank(groups = Groups.CadastroRestaurante.class) // nao pode ser nulo, vazio e em branco
    @Column(nullable = false)
    private String nome;

//    @DecimalMin("0")
    @PositiveOrZero(groups = Groups.CadastroRestaurante.class) // permitido apenas valor positivo ou zero
    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    @Valid // validando as propriedades da classe
    @NotNull(groups = Groups.CadastroRestaurante.class)
    @ManyToOne
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @JsonIgnore
    @Embedded
    private Endereco endereco;

    @JsonIgnore
    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime dataCadastro;

    @JsonIgnore
    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime dataAtualizacao;

    @JsonIgnore
    @ManyToMany
//     joinColumns -> define qual o nome da coluna da chave estrangeira da tabela intermediaria
    @JoinTable(name = "restaurante_forma_pagamento",
    joinColumns = @JoinColumn(name = "restaurante_id"),
    inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();

}

package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.core.validation.Groups;
import com.algaworks.algafood.core.validation.ValorZeroIncluiDescricao;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@ValorZeroIncluiDescricao(valorField = "taxaFrete", descricaoField = "nome",
        descricaoObrigatoria = "Frete Gratis")
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
//    @NotBlank // nao pode ser nulo, vazio e em branco
    @Column(nullable = false)
    private String nome;

//    @DecimalMin("0")
//    @NotNull
//    @PositiveOrZero // permitido apenas valor positivo ou zero
    @Column(name = "taxa_frete", nullable = false)
    private BigDecimal taxaFrete;

    // ignorando o nome na deserealizacao json para object
//    @Valid // validando as propriedades da classe
// no momento de validar a classe converte o group default para um group especifico
//    @ConvertGroup(from = Default.class, to = Groups.CadastroRestaurante.class)
//    @ConvertGroup(to = Groups.CozinhaID.class)
//    @NotNull
    @ManyToOne
    @JoinColumn(name = "cozinha_id", nullable = false)
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @ManyToMany
//     joinColumns -> define qual o nome da coluna da chave estrangeira da tabela intermediaria
    @JoinTable(name = "restaurante_forma_pagamento",
    joinColumns = @JoinColumn(name = "restaurante_id"),
    inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id"))
    private List<FormaPagamento> formasPagamento = new ArrayList<>();

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();

}

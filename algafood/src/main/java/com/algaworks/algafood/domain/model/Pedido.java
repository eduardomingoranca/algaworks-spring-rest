package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.domain.enumeration.StatusPedido;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private BigDecimal taxaFrete;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private LocalDateTime dataCadastro;

    @Column(columnDefinition = "datetime")
    private LocalDateTime dataConfirmacao;

    @Column(columnDefinition = "datetime")
    private LocalDateTime dataCancelamento;

    @Column(columnDefinition = "datetime")
    private LocalDateTime dataEntrega;

    @Column(nullable = false)
    private StatusPedido status;

    @ManyToOne
    @JoinColumn(name = "forma_pagamento_id", nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(name = "restaurante_id", nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario cliente;

    @Embedded
    private Endereco enderecoEntrega;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens = new ArrayList<>();

}

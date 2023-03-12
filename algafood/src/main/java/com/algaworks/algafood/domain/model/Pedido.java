package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.domain.enumeration.StatusPedido;
import com.algaworks.algafood.domain.exception.NegocioException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.algaworks.algafood.domain.enumeration.StatusPedido.*;
import static java.lang.String.format;
import static java.time.OffsetDateTime.now;
import static java.util.UUID.randomUUID;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Pedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String codigo;

    @Column(nullable = false)
    private BigDecimal subtotal;

    @Column(nullable = false)
    private BigDecimal taxaFrete;

    @Column(nullable = false)
    private BigDecimal valorTotal;

    @Embedded
    private Endereco enderecoEntrega;

    @Enumerated(STRING)
    private StatusPedido status = CRIADO;

    @CreationTimestamp
    private OffsetDateTime dataCriacao;

    @Column(columnDefinition = "datetime")
    private OffsetDateTime dataConfirmacao;

    @Column(columnDefinition = "datetime")
    private OffsetDateTime dataCancelamento;

    @Column(columnDefinition = "datetime")
    private OffsetDateTime dataEntrega;

    // LAZY -> realiza a consulta quando precisar/for utilizada
    @ManyToOne(fetch = LAZY)
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @OneToMany(mappedBy = "pedido", cascade = ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    public void confirmar() {
        setStatus(CONFIRMADO);
        setDataConfirmacao(now());
    }

    public void entregar() {
        setStatus(ENTREGUE);
        setDataEntrega(now());
    }

    public void cancelar() {
        setStatus(CANCELADO);
        setDataCancelamento(now());
    }

    private void setStatus(StatusPedido novoStatus) {
        if (getStatus().naoPodeAlterarPara(novoStatus)) {
            throw new NegocioException(format("O status do pedido %s nao pode ser alterado de %s para %s",
                    getCodigo(), getStatus().getDescricao(), novoStatus.getDescricao()));
        }

        this.status = novoStatus;
    }

    // antes de persistir a entidade execute o metodo
    @PrePersist
    private void gerarCodigo() {
        setCodigo(randomUUID().toString());
    }

}

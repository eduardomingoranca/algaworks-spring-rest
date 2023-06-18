package com.algaworks.algafood.domain.model;

import com.algaworks.algafood.domain.enumeration.StatusPedido;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.domain.AbstractAggregateRoot;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.algaworks.algafood.domain.enumeration.StatusPedido.CRIADO;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.util.UUID.randomUUID;

@Data
// callSuper = false -> caso nao queira chamar o equals and hash code da super class
// mas gerar um equals and hash code da classe atual
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
public class Pedido extends AbstractAggregateRoot<Pedido> {

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

    // antes de persistir a entidade execute o metodo
    @PrePersist
    private void gerarCodigo() {
        setCodigo(randomUUID().toString());
    }

}

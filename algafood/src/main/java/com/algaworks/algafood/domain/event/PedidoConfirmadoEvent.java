package com.algaworks.algafood.domain.event;

import com.algaworks.algafood.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

// classe que representa o evento
@Getter
@AllArgsConstructor
public class PedidoConfirmadoEvent {
    private Pedido pedido;
}

package com.tienda.pedidos.infrastructure.output.internal;

import com.tienda.pedidos.domain.model.PedidoCalculable;
import com.tienda.pedidos.domain.port.out.MensajeInternoPort;

public class ConsolaMensajeInternoAdapter implements MensajeInternoPort {

    @Override
    public void mostrarConfirmacionInterna(PedidoCalculable pedido) {
        System.out.printf("Mensaje interno: pedido %s registrado internamente.%n", pedido.getId());
    }
}

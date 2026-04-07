package com.tienda.pedidos.infrastructure.output.notification;

import com.tienda.pedidos.domain.model.PedidoCalculable;
import com.tienda.pedidos.domain.port.out.NotificadorClientePort;

public class ConsolaNotificadorClienteAdapter implements NotificadorClientePort {

    @Override
    public void notificarConfirmacion(PedidoCalculable pedido) {
        System.out.printf("Notificacion al cliente: el pedido %s ha sido confirmado con total %.2f%n",
                pedido.getId(),
                pedido.calcularTotal());
    }
}

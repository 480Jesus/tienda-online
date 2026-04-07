package com.tienda.pedidos.domain.port.out;

import com.tienda.pedidos.domain.model.PedidoCalculable;

public interface RegistroPedidoPort {

    void registrar(PedidoCalculable pedido);
}

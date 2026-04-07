package com.tienda.pedidos.domain.service;

import com.tienda.pedidos.domain.model.PedidoCalculable;

public class CalculadoraPedidoService {

    public double calcularTotal(PedidoCalculable pedido) {
        if (pedido == null) {
            throw new IllegalArgumentException("El pedido es obligatorio.");
        }
        return pedido.calcularTotal();
    }
}

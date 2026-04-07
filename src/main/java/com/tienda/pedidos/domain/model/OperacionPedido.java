package com.tienda.pedidos.domain.model;

import java.util.ArrayList;
import java.util.List;

public abstract class OperacionPedido implements PedidoCalculable {

    private final PedidoCalculable pedidoDecorado;

    protected OperacionPedido(PedidoCalculable pedidoDecorado) {
        if (pedidoDecorado == null) {
            throw new IllegalArgumentException("El pedido decorado es obligatorio.");
        }
        this.pedidoDecorado = pedidoDecorado;
    }

    public abstract TipoOperacion getTipo();

    public abstract String getDescripcion();

    protected abstract double aplicarOperacion(double subtotalActual);

    @Override
    public String getId() {
        return pedidoDecorado.getId();
    }

    @Override
    public double getImporteBase() {
        return pedidoDecorado.getImporteBase();
    }

    @Override
    public double calcularTotal() {
        return aplicarOperacion(pedidoDecorado.calcularTotal());
    }

    @Override
    public List<String> getOperaciones() {
        List<String> operaciones = new ArrayList<>(pedidoDecorado.getOperaciones());
        operaciones.add(getDescripcion());
        return operaciones;
    }

    @Override
    public boolean isConfirmado() {
        return pedidoDecorado.isConfirmado();
    }

    @Override
    public void confirmar() {
        pedidoDecorado.confirmar();
    }
}

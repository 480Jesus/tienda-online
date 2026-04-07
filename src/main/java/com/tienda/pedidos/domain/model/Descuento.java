package com.tienda.pedidos.domain.model;

public class Descuento extends OperacionPedido {

    private final double porcentaje;

    public Descuento(PedidoCalculable pedidoDecorado, double porcentaje) {
        super(pedidoDecorado);
        validarPorcentaje(porcentaje);
        this.porcentaje = porcentaje;
    }

    @Override
    public TipoOperacion getTipo() {
        return TipoOperacion.DESCUENTO;
    }

    @Override
    public String getDescripcion() {
        return String.format("Descuento %.2f%%", porcentaje);
    }

    @Override
    protected double aplicarOperacion(double subtotalActual) {
        return subtotalActual - (subtotalActual * porcentaje / 100.0);
    }

    private void validarPorcentaje(double porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("El descuento debe estar entre 0 y 100.");
        }
    }
}

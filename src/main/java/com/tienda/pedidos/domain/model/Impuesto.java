package com.tienda.pedidos.domain.model;

public class Impuesto extends OperacionPedido {

    private final double porcentaje;

    public Impuesto(PedidoCalculable pedidoDecorado, double porcentaje) {
        super(pedidoDecorado);
        validarPorcentaje(porcentaje);
        this.porcentaje = porcentaje;
    }

    @Override
    public TipoOperacion getTipo() {
        return TipoOperacion.IMPUESTO;
    }

    @Override
    public String getDescripcion() {
        return String.format("Impuesto %.2f%%", porcentaje);
    }

    @Override
    protected double aplicarOperacion(double subtotalActual) {
        return subtotalActual + (subtotalActual * porcentaje / 100.0);
    }

    private void validarPorcentaje(double porcentaje) {
        if (porcentaje < 0 || porcentaje > 100) {
            throw new IllegalArgumentException("El impuesto debe estar entre 0 y 100.");
        }
    }
}

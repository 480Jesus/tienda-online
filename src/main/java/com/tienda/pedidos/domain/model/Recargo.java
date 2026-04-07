package com.tienda.pedidos.domain.model;

public class Recargo extends OperacionPedido {

    private final double importe;

    public Recargo(PedidoCalculable pedidoDecorado, double importe) {
        super(pedidoDecorado);
        validarImporte(importe);
        this.importe = importe;
    }

    @Override
    public TipoOperacion getTipo() {
        return TipoOperacion.RECARGO;
    }

    @Override
    public String getDescripcion() {
        return String.format("Recargo %.2f", importe);
    }

    @Override
    protected double aplicarOperacion(double subtotalActual) {
        return subtotalActual + importe;
    }

    private void validarImporte(double importe) {
        if (importe < 0) {
            throw new IllegalArgumentException("El recargo no puede ser negativo.");
        }
    }
}

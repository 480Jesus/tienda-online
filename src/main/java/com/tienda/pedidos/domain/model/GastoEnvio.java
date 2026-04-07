package com.tienda.pedidos.domain.model;

public class GastoEnvio extends OperacionPedido {

    private final double importe;

    public GastoEnvio(PedidoCalculable pedidoDecorado, double importe) {
        super(pedidoDecorado);
        validarImporte(importe);
        this.importe = importe;
    }

    @Override
    public TipoOperacion getTipo() {
        return TipoOperacion.GASTO_ENVIO;
    }

    @Override
    public String getDescripcion() {
        return String.format("Gasto de envio %.2f", importe);
    }

    @Override
    protected double aplicarOperacion(double subtotalActual) {
        return subtotalActual + importe;
    }

    private void validarImporte(double importe) {
        if (importe < 0) {
            throw new IllegalArgumentException("El gasto de envio no puede ser negativo.");
        }
    }
}

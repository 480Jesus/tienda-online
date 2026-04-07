package com.tienda.pedidos.application.dto;

import com.tienda.pedidos.domain.model.TipoOperacion;

public class AplicarOperacionRequest {

    private final TipoOperacion tipoOperacion;
    private final double valor;

    public AplicarOperacionRequest(TipoOperacion tipoOperacion, double valor) {
        this.tipoOperacion = tipoOperacion;
        this.valor = valor;
    }

    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public double getValor() {
        return valor;
    }
}

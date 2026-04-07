package com.tienda.pedidos.application.dto;

public class CrearPedidoRequest {

    private final String id;
    private final double importeBase;

    public CrearPedidoRequest(String id, double importeBase) {
        this.id = id;
        this.importeBase = importeBase;
    }

    public String getId() {
        return id;
    }

    public double getImporteBase() {
        return importeBase;
    }
}

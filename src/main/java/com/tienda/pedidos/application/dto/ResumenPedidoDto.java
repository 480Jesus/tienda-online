package com.tienda.pedidos.application.dto;

import java.util.List;

public class ResumenPedidoDto {

    private final String id;
    private final double importeBase;
    private final List<String> operaciones;
    private final double total;
    private final boolean confirmado;

    public ResumenPedidoDto(String id, double importeBase, List<String> operaciones, double total, boolean confirmado) {
        this.id = id;
        this.importeBase = importeBase;
        this.operaciones = operaciones;
        this.total = total;
        this.confirmado = confirmado;
    }

    public String getId() {
        return id;
    }

    public double getImporteBase() {
        return importeBase;
    }

    public List<String> getOperaciones() {
        return operaciones;
    }

    public double getTotal() {
        return total;
    }

    public boolean isConfirmado() {
        return confirmado;
    }
}

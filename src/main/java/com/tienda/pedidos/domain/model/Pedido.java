package com.tienda.pedidos.domain.model;

import java.util.Collections;
import java.util.List;

public class Pedido implements PedidoCalculable {

    private final String id;
    private final double importeBase;
    private boolean confirmado;

    public Pedido(String id, double importeBase) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("El identificador del pedido es obligatorio.");
        }
        if (importeBase < 0) {
            throw new IllegalArgumentException("El importe base no puede ser negativo.");
        }
        this.id = id;
        this.importeBase = importeBase;
        this.confirmado = false;
    }

    @Override
    public void confirmar() {
        if (confirmado) {
            throw new IllegalStateException("El pedido ya fue confirmado.");
        }
        confirmado = true;
    }

    @Override
    public double calcularTotal() {
        return importeBase;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public double getImporteBase() {
        return importeBase;
    }

    @Override
    public List<String> getOperaciones() {
        return Collections.emptyList();
    }

    @Override
    public boolean isConfirmado() {
        return confirmado;
    }
}

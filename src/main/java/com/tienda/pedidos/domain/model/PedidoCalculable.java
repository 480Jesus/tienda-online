package com.tienda.pedidos.domain.model;

import java.util.List;

public interface PedidoCalculable {

    String getId();

    double getImporteBase();

    double calcularTotal();

    List<String> getOperaciones();

    boolean isConfirmado();

    void confirmar();
}

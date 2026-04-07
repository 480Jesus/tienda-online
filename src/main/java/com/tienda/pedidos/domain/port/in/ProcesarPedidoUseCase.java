package com.tienda.pedidos.domain.port.in;

import com.tienda.pedidos.application.dto.AplicarOperacionRequest;
import com.tienda.pedidos.application.dto.CrearPedidoRequest;
import com.tienda.pedidos.application.dto.ResumenPedidoDto;

public interface ProcesarPedidoUseCase {

    void crearPedido(CrearPedidoRequest request);

    void aplicarOperacion(AplicarOperacionRequest request);

    ResumenPedidoDto verResumenPedido();

    void confirmarPedido();
}

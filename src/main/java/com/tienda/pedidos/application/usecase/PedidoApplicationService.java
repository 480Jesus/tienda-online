package com.tienda.pedidos.application.usecase;

import com.tienda.pedidos.application.dto.AplicarOperacionRequest;
import com.tienda.pedidos.application.dto.CrearPedidoRequest;
import com.tienda.pedidos.application.dto.ResumenPedidoDto;
import com.tienda.pedidos.domain.model.Descuento;
import com.tienda.pedidos.domain.model.GastoEnvio;
import com.tienda.pedidos.domain.model.Impuesto;
import com.tienda.pedidos.domain.model.OperacionPedido;
import com.tienda.pedidos.domain.model.Pedido;
import com.tienda.pedidos.domain.model.PedidoCalculable;
import com.tienda.pedidos.domain.model.Recargo;
import com.tienda.pedidos.domain.model.TipoOperacion;
import com.tienda.pedidos.domain.port.in.ProcesarPedidoUseCase;
import com.tienda.pedidos.domain.port.out.MensajeInternoPort;
import com.tienda.pedidos.domain.port.out.NotificadorClientePort;
import com.tienda.pedidos.domain.port.out.RegistroPedidoPort;
import com.tienda.pedidos.domain.service.CalculadoraPedidoService;

import java.util.ArrayList;
import java.util.List;

public class PedidoApplicationService implements ProcesarPedidoUseCase {

    private final NotificadorClientePort notificadorClientePort;
    private final RegistroPedidoPort registroPedidoPort;
    private final MensajeInternoPort mensajeInternoPort;
    private final CalculadoraPedidoService calculadoraPedidoService;
    private Pedido pedidoBase;
    private PedidoCalculable pedidoActual;

    public PedidoApplicationService(NotificadorClientePort notificadorClientePort,
                                    RegistroPedidoPort registroPedidoPort,
                                    MensajeInternoPort mensajeInternoPort) {
        this.notificadorClientePort = notificadorClientePort;
        this.registroPedidoPort = registroPedidoPort;
        this.mensajeInternoPort = mensajeInternoPort;
        this.calculadoraPedidoService = new CalculadoraPedidoService();
    }

    @Override
    public void crearPedido(CrearPedidoRequest request) {
        validarCrearPedidoRequest(request);
        this.pedidoBase = new Pedido(request.getId(), request.getImporteBase());
        this.pedidoActual = pedidoBase;
    }

    @Override
    public void aplicarOperacion(AplicarOperacionRequest request) {
        PedidoCalculable pedido = obtenerPedidoActual();
        if (request == null || request.getTipoOperacion() == null) {
            throw new IllegalArgumentException("La operacion solicitada no es valida.");
        }
        if (pedido.isConfirmado()) {
            throw new IllegalStateException("No se pueden aplicar operaciones a un pedido confirmado.");
        }

        pedidoActual = crearOperacion(request.getTipoOperacion(), request.getValor(), pedido);
    }

    @Override
    public ResumenPedidoDto verResumenPedido() {
        PedidoCalculable pedido = obtenerPedidoActual();
        List<String> operaciones = new ArrayList<>(pedido.getOperaciones());

        return new ResumenPedidoDto(
                pedido.getId(),
                pedido.getImporteBase(),
                operaciones,
                calculadoraPedidoService.calcularTotal(pedido),
                pedido.isConfirmado()
        );
    }

    @Override
    public void confirmarPedido() {
        PedidoCalculable pedido = obtenerPedidoActual();
        pedido.confirmar();
        registroPedidoPort.registrar(pedido);
        notificadorClientePort.notificarConfirmacion(pedido);
        mensajeInternoPort.mostrarConfirmacionInterna(pedido);
        pedidoBase = null;
        pedidoActual = null;
    }

    private void validarCrearPedidoRequest(CrearPedidoRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("La solicitud de creacion es obligatoria.");
        }
    }

    private PedidoCalculable obtenerPedidoActual() {
        if (pedidoActual == null) {
            throw new IllegalStateException("No hay ningun pedido creado en este momento.");
        }
        return pedidoActual;
    }

    private OperacionPedido crearOperacion(TipoOperacion tipoOperacion, double valor, PedidoCalculable pedido) {
        return switch (tipoOperacion) {
            case DESCUENTO -> new Descuento(pedido, valor);
            case IMPUESTO -> new Impuesto(pedido, valor);
            case GASTO_ENVIO -> new GastoEnvio(pedido, valor);
            case RECARGO -> new Recargo(pedido, valor);
        };
    }
}

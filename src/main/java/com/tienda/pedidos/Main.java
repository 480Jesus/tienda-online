package com.tienda.pedidos;

import com.tienda.pedidos.application.usecase.PedidoApplicationService;
import com.tienda.pedidos.domain.port.in.ProcesarPedidoUseCase;
import com.tienda.pedidos.domain.port.out.MensajeInternoPort;
import com.tienda.pedidos.domain.port.out.NotificadorClientePort;
import com.tienda.pedidos.domain.port.out.RegistroPedidoPort;
import com.tienda.pedidos.infrastructure.input.cli.PedidoCliController;
import com.tienda.pedidos.infrastructure.output.internal.ConsolaMensajeInternoAdapter;
import com.tienda.pedidos.infrastructure.output.notification.ConsolaNotificadorClienteAdapter;
import com.tienda.pedidos.infrastructure.output.persistence.FicheroRegistroPedidoAdapter;

public class Main {

    public static void main(String[] args) {
        NotificadorClientePort notificadorClientePort = new ConsolaNotificadorClienteAdapter();
        RegistroPedidoPort registroPedidoPort = new FicheroRegistroPedidoAdapter("pedidos.txt");
        MensajeInternoPort mensajeInternoPort = new ConsolaMensajeInternoAdapter();

        ProcesarPedidoUseCase procesarPedidoUseCase = new PedidoApplicationService(
                notificadorClientePort,
                registroPedidoPort,
                mensajeInternoPort
        );

        PedidoCliController cliController = new PedidoCliController(procesarPedidoUseCase);
        cliController.iniciar();
    }
}

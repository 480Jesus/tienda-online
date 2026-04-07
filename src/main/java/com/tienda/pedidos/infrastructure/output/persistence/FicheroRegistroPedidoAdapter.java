package com.tienda.pedidos.infrastructure.output.persistence;

import com.tienda.pedidos.domain.model.PedidoCalculable;
import com.tienda.pedidos.domain.port.out.RegistroPedidoPort;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class FicheroRegistroPedidoAdapter implements RegistroPedidoPort {

    private final Path rutaFichero;

    public FicheroRegistroPedidoAdapter(String nombreFichero) {
        this.rutaFichero = Path.of(nombreFichero);
    }

    @Override
    public void registrar(PedidoCalculable pedido) {
        List<String> lineas = new ArrayList<>();
        lineas.add("Pedido confirmado");
        lineas.add("Id: " + pedido.getId());
        lineas.add(String.format("Importe base: %.2f", pedido.getImporteBase()));
        lineas.add("Operaciones:");

        if (pedido.getOperaciones().isEmpty()) {
            lineas.add("- Sin operaciones");
        } else {
            for (String operacion : pedido.getOperaciones()) {
                lineas.add("- " + operacion);
            }
        }

        lineas.add(String.format("Total final: %.2f", pedido.calcularTotal()));
        lineas.add("----------------------------------------");

        try {
            Files.write(
                    rutaFichero,
                    lineas,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException ex) {
            throw new IllegalStateException("No se pudo registrar el pedido en fichero.", ex);
        }
    }
}

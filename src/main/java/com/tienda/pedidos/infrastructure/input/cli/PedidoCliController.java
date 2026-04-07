package com.tienda.pedidos.infrastructure.input.cli;

import com.tienda.pedidos.application.dto.AplicarOperacionRequest;
import com.tienda.pedidos.application.dto.CrearPedidoRequest;
import com.tienda.pedidos.application.dto.ResumenPedidoDto;
import com.tienda.pedidos.domain.model.TipoOperacion;
import com.tienda.pedidos.domain.port.in.ProcesarPedidoUseCase;

import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class PedidoCliController {

    private final ProcesarPedidoUseCase procesarPedidoUseCase;
    private final Scanner scanner;

    public PedidoCliController(ProcesarPedidoUseCase procesarPedidoUseCase) {
        this.procesarPedidoUseCase = procesarPedidoUseCase;
        this.scanner = new Scanner(System.in);
        Locale.setDefault(Locale.US);
    }

    public void iniciar() {
        boolean salir = false;
        while (!salir) {
            mostrarMenu();
            int opcion = leerEntero("Selecciona una opcion: ");

            try {
                switch (opcion) {
                    case 1 -> crearPedido();
                    case 2 -> aplicarOperacion(TipoOperacion.DESCUENTO, "Introduce el porcentaje de descuento: ");
                    case 3 -> aplicarOperacion(TipoOperacion.IMPUESTO, "Introduce el porcentaje de impuesto: ");
                    case 4 -> aplicarOperacion(TipoOperacion.GASTO_ENVIO, "Introduce el importe del gasto de envio: ");
                    case 5 -> aplicarOperacion(TipoOperacion.RECARGO, "Introduce el importe del recargo: ");
                    case 6 -> mostrarResumen();
                    case 7 -> confirmarPedido();
                    case 8 -> salir = true;
                    default -> System.out.println("Opcion no valida.");
                }
            } catch (IllegalArgumentException | IllegalStateException ex) {
                System.out.println("Error: " + ex.getMessage());
            }

            System.out.println();
        }

        System.out.println("Aplicacion finalizada.");
    }

    private void mostrarMenu() {
        System.out.println("===== GESTION DE PEDIDOS =====");
        System.out.println("1. Crear pedido");
        System.out.println("2. Aplicar descuento");
        System.out.println("3. Aplicar impuesto");
        System.out.println("4. Aplicar gastos de envio");
        System.out.println("5. Aplicar recargo");
        System.out.println("6. Ver resumen");
        System.out.println("7. Confirmar pedido");
        System.out.println("8. Salir");
    }

    private void crearPedido() {
        System.out.print("Introduce el identificador del pedido: ");
        String id = scanner.nextLine().trim();
        double importeBase = leerDouble("Introduce el importe base: ");

        procesarPedidoUseCase.crearPedido(new CrearPedidoRequest(id, importeBase));
        System.out.println("Pedido creado correctamente.");
    }

    private void aplicarOperacion(TipoOperacion tipoOperacion, String mensaje) {
        double valor = leerDouble(mensaje);
        procesarPedidoUseCase.aplicarOperacion(new AplicarOperacionRequest(tipoOperacion, valor));
        System.out.println("Operacion aplicada correctamente.");
    }

    private void mostrarResumen() {
        ResumenPedidoDto resumen = procesarPedidoUseCase.verResumenPedido();
        System.out.println("----- RESUMEN DEL PEDIDO -----");
        System.out.println("Id: " + resumen.getId());
        System.out.printf("Importe base: %.2f%n", resumen.getImporteBase());
        System.out.println("Operaciones:");

        List<String> operaciones = resumen.getOperaciones();
        if (operaciones.isEmpty()) {
            System.out.println("  Sin operaciones aplicadas");
        } else {
            for (String operacion : operaciones) {
                System.out.println("  - " + operacion);
            }
        }

        System.out.printf("Total final: %.2f%n", resumen.getTotal());
        System.out.println("Confirmado: " + (resumen.isConfirmado() ? "Si" : "No"));
    }

    private void confirmarPedido() {
        procesarPedidoUseCase.confirmarPedido();
        System.out.println("Pedido confirmado y procesado correctamente.");
    }

    private int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException ex) {
                System.out.println("Debes introducir un numero entero.");
            }
        }
    }

    private double leerDouble(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String entrada = scanner.nextLine().trim().replace(',', '.');
            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException ex) {
                System.out.println("Debes introducir un numero valido.");
            }
        }
    }
}

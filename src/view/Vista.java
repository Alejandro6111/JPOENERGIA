package view;

import controller.Controlador;
import model.Cliente;
import model.Consumo;
import model.Registrador;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Se encarga de mostrar la información al usuario y de recibir lo que el usuario escribe.
 * Es la "cara" de la aplicación. Habla con el Controlador para hacer las cosas.
 */
public class Vista {
    /** El controlador que se encarga de la lógica del programa. */
    private Controlador controlador;
    /** Herramienta para leer lo que el usuario escribe en la consola. */
    private Scanner scanner;
    /** Ayuda a convertir texto a fechas/horas y viceversa, en un formato específico. */
    private DateTimeFormatter formateadorFechaHora = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");


    /**
     * Constructor de la Vista. Prepara el controlador y el scanner para usarlos.
     */
    public Vista() {
        this.controlador = new Controlador(); // Crea el "cerebro" de la aplicación.
        this.scanner = new Scanner(System.in); // Prepara para leer desde la consola.
    }

    /**
     * Muestra el menú principal y maneja las opciones que elija el usuario,
     * hasta que decida salir.
     */
    public void mIniciar() {
        int opcionElegida;
        do {
            System.out.println("\n--- Menú Principal: Gestión de Consumo Eléctrico ---");
            System.out.println("1.  Crear un nuevo Cliente");
            System.out.println("2.  Editar información de un Cliente existente");
            System.out.println("3.  Añadir un nuevo Medidor (Registrador) a un Cliente");
            System.out.println("4.  Editar información de un Medidor");
            System.out.println("5.  Cargar consumos automáticamente (para TODOS los clientes, un mes/año)");
            System.out.println("6.  Cargar consumos automáticamente (para UN cliente, un mes/año)");
            System.out.println("7.  Registrar o cambiar un Consumo específico (para un medidor, fecha y kWh)");
            System.out.println("8.  Ver todos los Consumos de un Medidor (para un mes/año)");
            System.out.println("9.  Generar Factura (en texto) de un Cliente (para un mes/año)");
            System.out.println("10. Ver Consumo MÍNIMO de un Cliente (para un mes/año)");
            System.out.println("11. Ver Consumo MÁXIMO de un Cliente (para un mes/año)");
            System.out.println("12. Ver Consumo por FRANJAS HORARIAS de un Cliente (para un mes/año)");
            System.out.println("13. Ver Consumo por DÍAS de un Cliente (para un mes/año)");
            System.out.println("14. Calcular VALOR TOTAL de la Factura de un Cliente (para un mes/año)");
            System.out.println("15. Mostrar lista de todos los Clientes");
            System.out.println("16. Mostrar lista de Medidores de un Cliente");

            System.out.println("0. Salir de la aplicación");
            System.out.print("Por favor, elija una opción: ");

            try {
                opcionElegida = scanner.nextInt();
                scanner.nextLine(); // Importante para limpiar el "Enter" que queda después de leer un número.

                switch (opcionElegida) {
                    case 1 -> mMenuCrearCliente();
                    case 2 -> mMenuEditarCliente();
                    case 3 -> mMenuCrearRegistrador();
                    case 4 -> mMenuEditarRegistrador();
                    case 5 -> mMenuCargarConsumosTodos();
                    case 6 -> mMenuCargarConsumosUnCliente();
                    case 7 -> mMenuAgregarModificarConsumoPuntual();
                    case 8 -> mMenuVerConsumosRegistrador();
                    case 9 -> mMenuGenerarFacturaCliente();
                    case 10 -> mMenuHallarConsumoMinimo();
                    case 11 -> mMenuHallarConsumoMaximo();
                    case 12 -> mMenuHallarConsumoPorFranjas();
                    case 13 -> mMenuHallarConsumoPorDias();
                    case 14 -> mMenuCalcularValorFactura();
                    case 15 -> mMenuListarClientes();
                    case 16 -> mMenuListarRegistradoresDeCliente();
                    case 0 -> System.out.println("Cerrando la aplicación...");
                    default -> System.out.println("Opción no reconocida. Por favor, intente de nuevo.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Entrada incorrecta. Debe ingresar un número para la opción.");
                scanner.nextLine(); // Limpia el scanner si hubo un error.
                opcionElegida = -1; // Para que el bucle continúe.
            }
        } while (opcionElegida != 0);
        scanner.close(); // Cierra el scanner cuando ya no se necesita.
    }

    /** Métodos privados para manejar cada opción del menú. Hacen la "interfaz" con el usuario. */

    private void mMenuCrearCliente() {
        System.out.println("\n--- Crear Nuevo Cliente ---");
        System.out.print("Número de identificación del cliente: ");
        String numeroIdentificacion = scanner.nextLine();
        System.out.print("Tipo de identificación (Ej: CC, NIT, Pasaporte): ");
        String tipoIdentificacion = scanner.nextLine();
        System.out.print("Correo electrónico: ");
        String correoElectronico = scanner.nextLine();
        System.out.print("Dirección física: ");
        String direccionFisica = scanner.nextLine();

        boolean seCreo = controlador.mCrearCliente(numeroIdentificacion, tipoIdentificacion, correoElectronico, direccionFisica);
        if (seCreo) {
            System.out.println("¡Cliente creado con éxito!");
        } else {
            System.out.println("Error: Parece que ya hay un cliente con ese número de identificación.");
        }
    }

    private void mMenuEditarCliente() {
        System.out.println("\n--- Editar Cliente ---");
        System.out.print("Número de identificación del cliente que desea editar: ");
        String numeroIdentificacion = scanner.nextLine();
        // Podríamos verificar primero si el cliente existe antes de pedir todos los datos.
        System.out.print("Nuevo tipo de identificación (deje en blanco si no cambia): ");
        String tipoIdentificacion = scanner.nextLine();
        System.out.print("Nuevo correo electrónico (deje en blanco si no cambia): ");
        String correoElectronico = scanner.nextLine();
        System.out.print("Nueva dirección física (deje en blanco si no cambia): ");
        String direccionFisica = scanner.nextLine();

        // Nota: Para una mejor experiencia, se debería obtener el cliente primero
        // y luego rellenar los campos con los valores actuales si se dejan en blanco.
        // Por simplicidad, aquí se asume que se ingresan todos los nuevos valores.
        Cliente clienteEditado = controlador.mEditarCliente(numeroIdentificacion, tipoIdentificacion, correoElectronico, direccionFisica);
        if (clienteEditado != null) {
            System.out.println("Cliente actualizado con éxito: " + clienteEditado);
        } else {
            System.out.println("Error: No se encontró un cliente con ese número de identificación.");
        }
    }

    private void mMenuCrearRegistrador() {
        System.out.println("\n--- Añadir Nuevo Medidor (Registrador) ---");
        System.out.print("Número de identificación del Cliente al que pertenece el medidor: ");
        String idCliente = scanner.nextLine();
        System.out.print("Número de identificación para el nuevo medidor: ");
        String idRegistrador = scanner.nextLine();
        System.out.print("Dirección donde se instalará el medidor: ");
        String direccion = scanner.nextLine();
        System.out.print("Ciudad donde se ubica el medidor: ");
        String ciudad = scanner.nextLine();

        Registrador registradorCreado = controlador.mCrearRegistrador(idRegistrador, direccion, ciudad, idCliente);
        if (registradorCreado != null) {
            System.out.println("Medidor añadido y asociado al cliente con éxito: " + registradorCreado);
        } else {
            System.out.println("Error: No se pudo añadir el medidor. Verifique el ID del cliente o si ya existe un medidor con ese ID para el cliente.");
        }
    }

    private void mMenuEditarRegistrador() {
        System.out.println("\n--- Editar Medidor (Registrador) ---");
        System.out.print("Número de identificación del Cliente dueño del medidor: ");
        String idCliente = scanner.nextLine();
        System.out.print("Número de identificación del medidor que desea editar: ");
        String idRegistrador = scanner.nextLine();
        // Similar a editar cliente, sería bueno mostrar datos actuales y permitir cambios parciales.
        System.out.print("Nueva dirección del medidor: ");
        String direccion = scanner.nextLine();
        System.out.print("Nueva ciudad del medidor: ");
        String ciudad = scanner.nextLine();

        Registrador registradorEditado = controlador.mEditarRegistrador(idRegistrador, direccion, ciudad, idCliente);
        if (registradorEditado != null) {
            System.out.println("Medidor actualizado con éxito: " + registradorEditado);
        } else {
            System.out.println("Error: No se pudo editar. Verifique los IDs del cliente y del medidor.");
        }
    }

    private void mMenuCargarConsumosTodos() {
        System.out.println("\n--- Cargar Consumos Automáticos (Para Todos los Clientes) ---");
        System.out.print("Ingrese el año (ej. 2025): ");
        int anio = scanner.nextInt();
        System.out.print("Ingrese el mes (número del 1 al 12): ");
        int mes = scanner.nextInt();
        scanner.nextLine(); // Limpiar

        if (mes < 1 || mes > 12 || anio < 1900) {
            System.out.println("Año o mes no válidos.");
            return;
        }
        controlador.mCargarConsumosAutomaticosTodosClientes(anio, mes);
        System.out.println("Se han simulado y cargado los consumos para todos los clientes para el periodo " + mes + "/" + anio + ".");
    }

    private void mMenuCargarConsumosUnCliente() {
        System.out.println("\n--- Cargar Consumos Automáticos (Para Un Cliente) ---");
        System.out.print("Número de identificación del Cliente: ");
        String idCliente = scanner.nextLine();
        System.out.print("Ingrese el año (ej. 2025): ");
        int anio = scanner.nextInt();
        System.out.print("Ingrese el mes (número del 1 al 12): ");
        int mes = scanner.nextInt();
        scanner.nextLine(); // Limpiar

        if (mes < 1 || mes > 12 || anio < 1900) {
            System.out.println("Año o mes no válidos.");
            return;
        }

        boolean seCargaron = controlador.mCargarConsumosAutomaticosUnCliente(idCliente, anio, mes);
        if (seCargaron) {
            System.out.println("Consumos simulados y cargados para el cliente " + idCliente + " para el periodo " + mes + "/" + anio + ".");
        } else {
            System.out.println("Error: No se encontró el cliente con ID " + idCliente + ".");
        }
    }

    private void mMenuAgregarModificarConsumoPuntual() {
        System.out.println("\n--- Registrar o Modificar un Consumo Específico ---");
        System.out.print("Número de identificación del Cliente: ");
        String idCliente = scanner.nextLine();
        System.out.print("Número de identificación del Medidor: ");
        String idRegistrador = scanner.nextLine();
        System.out.print("Fecha y hora del consumo (formato AAAA-MM-DDTHH:mm, ej: 2025-05-15T14:30): ");
        String fechaHoraTexto = scanner.nextLine();
        System.out.print("Cantidad de kWh consumidos (ej: 150.75): ");
        double kWh = -1;
        try {
            kWh = scanner.nextDouble();
        } catch (java.util.InputMismatchException e) {
            System.out.println("Error: La cantidad de kWh debe ser un número.");
            scanner.nextLine(); // Limpiar
            return;
        }
        scanner.nextLine(); // Limpiar

        try {
            LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraTexto, formateadorFechaHora);
            Consumo consumoParaRegistrar = new Consumo(fechaHora, kWh); // Esto valida kWh >= 0
            
            boolean seRegistro = controlador.mAgregarConsumoRegistrador(idCliente, idRegistrador, consumoParaRegistrar);

            if (seRegistro) {
                System.out.println("Consumo registrado/modificado con éxito.");
            } else {
                System.out.println("Error al registrar/modificar el consumo. Verifique los IDs o si el mes/año está inicializado en el medidor.");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Error: El formato de la fecha y hora es incorrecto. Debe ser AAAA-MM-DDTHH:mm (ej: 2025-07-23T09:00).");
        } catch (IllegalArgumentException e) { // Captura errores de Consumo (kWh negativos) o del controlador.
            System.out.println("Error en los datos: " + e.getMessage());
        }
    }

    private void mMenuVerConsumosRegistrador() {
        System.out.println("\n--- Ver Consumos de un Medidor ---");
        System.out.print("Número de identificación del Cliente: ");
        String idCliente = scanner.nextLine();
        System.out.print("Número de identificación del Medidor: ");
        String idRegistrador = scanner.nextLine();
        System.out.print("Ingrese el año de los consumos a ver (ej. 2025): ");
        int anio = scanner.nextInt();
        System.out.print("Ingrese el mes (número del 1 al 12): ");
        int mes = scanner.nextInt();
        scanner.nextLine(); // Limpiar

        if (mes < 1 || mes > 12 || anio < 1900) {
            System.out.println("Año o mes no válidos.");
            return;
        }
        
        List<Consumo> listaDeConsumos = controlador.mObtenerConsumosRegistrador(idCliente, idRegistrador, anio, mes);
        
        if (listaDeConsumos != null && !listaDeConsumos.isEmpty()) {
            System.out.println("Consumos del medidor " + idRegistrador + " (Cliente: " + idCliente + ") para " + mes + "/" + anio + ":");
            for (Consumo c : listaDeConsumos) {
                // Muestra cada consumo con su fecha, kWh y costo calculado.
                System.out.println("  " + c.mGetFechaHora().format(formateadorFechaHora) + 
                                   " - kWh: " + String.format("%.2f", c.mGetKWh()) + 
                                   " - Costo: " + String.format("%.2f", c.mCalcularCosto()) + " COP");
            }
        } else {
            System.out.println("No se encontraron consumos para el medidor y periodo que especificó, o los datos no están cargados para ese periodo.");
        }
    }

    private void mMenuGenerarFacturaCliente() {
        System.out.println("\n--- Generar Factura (en texto) del Cliente ---");
        System.out.print("Número de identificación del Cliente: ");
        String idCliente = scanner.nextLine();
        System.out.print("Ingrese el año de la factura (ej. 2025): ");
        int anio = scanner.nextInt();
        System.out.print("Ingrese el mes de la factura (número del 1 al 12): ");
        int mes = scanner.nextInt();
        scanner.nextLine(); // Limpiar

        if (mes < 1 || mes > 12 || anio < 1900) {
            System.out.println("Año o mes no válidos.");
            return;
        }

        String facturaEnTexto = controlador.mGenerarFacturaTextoCliente(idCliente, anio, mes);
        System.out.println("\n--- INICIO DE LA FACTURA (TEXTO) ---");
        System.out.println(facturaEnTexto);
        System.out.println("--- FIN DE LA FACTURA ---");
    }

    private void mMenuHallarConsumoMinimo() {
        System.out.println("\n--- Ver Consumo Mínimo Horario del Cliente por Mes ---");
        System.out.print("Número de identificación del Cliente: ");
        String idCliente = scanner.nextLine();
        System.out.print("Ingrese el año (ej. 2025): ");
        int anio = scanner.nextInt();
        System.out.print("Ingrese el mes (número del 1 al 12): ");
        int mes = scanner.nextInt();
        scanner.nextLine(); // Limpiar
        
        if (mes < 1 || mes > 12 || anio < 1900) {
            System.out.println("Año o mes no válidos.");
            return;
        }

        double minimo = controlador.mHallarConsumoMinimoClienteMes(idCliente, anio, mes);
        if (minimo == Double.MAX_VALUE) { // Si el controlador devuelve esto, es que no hay datos.
            System.out.println("No se encontraron datos de consumo para el cliente y periodo que especificó.");
        } else {
            System.out.println(String.format("El consumo horario MÁS BAJO para el cliente %s en %02d/%d fue de: %.2f kWh", idCliente, mes, anio, minimo));
        }
    }

    private void mMenuHallarConsumoMaximo() {
        System.out.println("\n--- Ver Consumo Máximo Horario del Cliente por Mes ---");
        System.out.print("Número de identificación del Cliente: ");
        String idCliente = scanner.nextLine();
        System.out.print("Ingrese el año (ej. 2025): ");
        int anio = scanner.nextInt();
        System.out.print("Ingrese el mes (número del 1 al 12): ");
        int mes = scanner.nextInt();
        scanner.nextLine(); // Limpiar

        if (mes < 1 || mes > 12 || anio < 1900) {
            System.out.println("Año o mes no válidos.");
            return;
        }

        double maximo = controlador.mHallarConsumoMaximoClienteMes(idCliente, anio, mes);
         if (maximo == Double.MIN_VALUE) { // Si el controlador devuelve esto, es que no hay datos.
            System.out.println("No se encontraron datos de consumo para el cliente y periodo que especificó.");
        } else {
            System.out.println(String.format("El consumo horario MÁS ALTO para el cliente %s en %02d/%d fue de: %.2f kWh", idCliente, mes, anio, maximo));
        }
    }

    private void mMenuHallarConsumoPorFranjas() {
        System.out.println("\n--- Ver Consumo Total por Franjas Horarias del Cliente (Mes) ---");
        System.out.print("Número de identificación del Cliente: ");
        String idCliente = scanner.nextLine();
        System.out.print("Ingrese el año (ej. 2025): ");
        int anio = scanner.nextInt();
        System.out.print("Ingrese el mes (número del 1 al 12): ");
        int mes = scanner.nextInt();
        scanner.nextLine(); // Limpiar
        
        if (mes < 1 || mes > 12 || anio < 1900) {
            System.out.println("Año o mes no válidos.");
            return;
        }

        double[] consumosPorFranja = controlador.mHallarConsumoPorFranjasClienteMes(idCliente, anio, mes);
        if (consumosPorFranja == null) {
            System.out.println("No se encontraron datos de consumo para el cliente y periodo que especificó.");
        } else {
            System.out.println("Consumo total para el cliente " + idCliente + " en " + mes + "/" + anio + ", por franja horaria:");
            System.out.println(String.format("  Franja 1 (00:00-06:00): %.2f kWh", consumosPorFranja[0]));
            System.out.println(String.format("  Franja 2 (07:00-17:00): %.2f kWh", consumosPorFranja[1]));
            System.out.println(String.format("  Franja 3 (18:00-23:00): %.2f kWh", consumosPorFranja[2]));
        }
    }

    private void mMenuHallarConsumoPorDias() {
        System.out.println("\n--- Ver Consumo Total por Días del Cliente (Mes) ---");
        System.out.print("Número de identificación del Cliente: ");
        String idCliente = scanner.nextLine();
        System.out.print("Ingrese el año (ej. 2025): ");
        int anio = scanner.nextInt();
        System.out.print("Ingrese el mes (número del 1 al 12): ");
        int mes = scanner.nextInt();
        scanner.nextLine(); // Limpiar
        
        if (mes < 1 || mes > 12 || anio < 1900) {
            System.out.println("Año o mes no válidos.");
            return;
        }

        double[] consumosPorDia = controlador.mHallarConsumoPorDiasClienteMes(idCliente, anio, mes);
        if (consumosPorDia == null) {
            System.out.println("No se encontraron datos de consumo para el cliente y periodo que especificó.");
        } else {
            System.out.println("Consumo total por día para el cliente " + idCliente + " en " + mes + "/" + anio + ":");
            for (int i = 0; i < consumosPorDia.length; i++) {
                System.out.println(String.format("  Día %02d del mes: %.2f kWh", (i + 1), consumosPorDia[i]));
            }
        }
    }

    private void mMenuCalcularValorFactura() {
        System.out.println("\n--- Calcular Valor Total de la Factura del Cliente (Mes) ---");
        System.out.print("Número de identificación del Cliente: ");
        String idCliente = scanner.nextLine();
        System.out.print("Ingrese el año (ej. 2025): ");
        int anio = scanner.nextInt();
        System.out.print("Ingrese el mes (número del 1 al 12): ");
        int mes = scanner.nextInt();
        scanner.nextLine(); // Limpiar

        if (mes < 1 || mes > 12 || anio < 1900) {
            System.out.println("Año o mes no válidos.");
            return;
        }

        double valorTotal = controlador.mCalcularValorFacturaClienteMes(idCliente, anio, mes);
        if (valorTotal < 0) { // El controlador devuelve -1.0 si no hay datos o hay error.
            System.out.println("No se pudo calcular la factura. Verifique que el cliente exista y tenga consumos cargados para " + mes + "/" + anio + ".");
        } else {
            System.out.println(String.format("El VALOR TOTAL de la factura para el cliente %s en %02d/%d es: %.2f COP", idCliente, mes, anio, valorTotal));
        }
    }

    private void mMenuListarClientes() {
        System.out.println("\n--- Lista de Todos los Clientes Registrados ---");
        List<Cliente> todosLosClientes = controlador.mGetClientes();
        if (todosLosClientes.isEmpty()) {
            System.out.println("Aún no hay clientes registrados en el sistema.");
        } else {
            for (Cliente cliente : todosLosClientes) {
                System.out.println(cliente); // Llama al método toString() de Cliente.
            }
        }
    }

    private void mMenuListarRegistradoresDeCliente() {
        System.out.println("\n--- Lista de Medidores de un Cliente Específico ---");
        System.out.print("Número de identificación del Cliente: ");
        String idCliente = scanner.nextLine();

        // Buscamos al cliente para poder listar sus medidores.
        Cliente clienteEncontrado = null;
        List<Cliente> todosLosClientes = controlador.mGetClientes();
        for(Cliente c : todosLosClientes) {
            if(c.mGetNumeroIdentificacion().equals(idCliente)){
                clienteEncontrado = c;
                break;
            }
        }

        if (clienteEncontrado == null) {
            System.out.println("No se encontró un cliente con el ID: " + idCliente);
            return;
        }

        List<Registrador> medidoresDelCliente = clienteEncontrado.mGetRegistradores();
        if (medidoresDelCliente.isEmpty()) {
            System.out.println("El cliente " + idCliente + " no tiene medidores (registradores) asociados actualmente.");
        } else {
            System.out.println("Medidores asociados al cliente " + idCliente + ":");
            for (Registrador medidor : medidoresDelCliente) {
                System.out.println("  " + medidor); // Llama al método toString() de Registrador.
            }
        }
    }
}
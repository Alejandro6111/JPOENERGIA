package view;

import controller.Controlador;
import model.Cliente;
import model.Registrador;
import model.Consumo;
import java.util.List;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Vista {
    private Controlador controlador;
    private Scanner scanner;

    public Vista() {
        this.controlador = new Controlador();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar(){
        int opcion;
        do {
            System.out.println("\n--- Menu Principal: ---");
            System.out.println("1. Crear Cliente");
            System.out.println("2. Editar Cliente");
            System.out.println("3. Crear Registrador");
            System.out.println("4. Editar Registrador");
            System.out.println("5. Agregar Consumo");
            System.out.println("6. Ver Consumos de un Registrador");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opcion: ");
            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar el buffer del scanner

            switch (opcion) {
                case 1 -> crearCliente();
                case 2 -> editarCliente(); 
                case 3 -> crearRegistrador(); 
                case 4 -> editarRegistrador();
                case 5 -> agregarConsumo(); 
                case 6 -> verConsumosRegistrador(); 
                case 7 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion no valida, intente de nuevo.");
            }
        } while (opcion != 0);
    }

    private void crearCliente() {
        System.out.print("Ingrese numero de identificacion: ");
        String numeroIdentificacion = scanner.nextLine();
        System.out.print("Ingrese tipo de identificacion: ");
        String tipoIdentificacion = scanner.nextLine();
        System.out.print("Ingrese correo electronico: ");
        String correoElectronico = scanner.nextLine();
        System.out.print("Ingrese direccion fisica: ");
        String direccionFisica = scanner.nextLine();

        boolean exito = controlador.crearCliente(numeroIdentificacion, tipoIdentificacion, correoElectronico, direccionFisica);
        if (exito) {
            System.out.println("Cliente creado exitosamente.");
        } else {
            System.out.println("Error al crear el cliente.");
        }
    }

    private void editarCliente() {
        System.out.print("Ingrese numero de identificacion del cliente a editar: ");
        String numeroIdentificacion = scanner.nextLine();
        System.out.print("Ingrese nuevo tipo de identificacion: ");
        String tipoIdentificacion = scanner.nextLine();
        System.out.print("Ingrese nuevo correo electronico: ");
        String correoElectronico = scanner.nextLine();
        System.out.print("Ingrese nueva direccion fisica: ");
        String direccionFisica = scanner.nextLine();

        Cliente cliente = controlador.editarCliente(numeroIdentificacion, tipoIdentificacion, correoElectronico, direccionFisica);
        if (cliente != null) {
            System.out.println("Cliente editado: " + cliente);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void crearRegistrador() {
        System.out.print("Ingrese numero de identificacion del registrador: ");
        String numeroIdentificacion = scanner.nextLine();
        System.out.print("Ingrese direccion del registrador: ");
        String direccion = scanner.nextLine();
        System.out.print("Ingrese ciudad del registrador: ");
        String ciudad = scanner.nextLine();
        System.out.print("Ingrese numero de cliente asociado: ");
        String numeroCliente = scanner.nextLine();

        Registrador registrador = controlador.crearRegistrador(numeroIdentificacion, direccion, ciudad, numeroCliente);
        if (registrador != null) {
            System.out.println("Registrador creado: " + registrador);
        } else {
            System.out.println("Cliente no encontrado.");
        }
    }

    private void editarRegistrador() {
        System.out.print("Ingrese numero de identificacion del registrador a editar: ");
        String numeroIdentificacion = scanner.nextLine();
        System.out.print("Ingrese nueva direccion del registrador: ");
        String direccion = scanner.nextLine();
        System.out.print("Ingrese nueva ciudad del registrador: ");
        String ciudad = scanner.nextLine();
        System.out.print("Ingrese numero de cliente asociado: ");
        String numeroCliente = scanner.nextLine();

        Registrador registrador = controlador.editarRegistrador(numeroIdentificacion, direccion, ciudad, numeroCliente);
        if (registrador != null) {
            System.out.println("Registrador editado: " + registrador);
        } else {
            System.out.println("Registrador o cliente no encontrado.");
        }
    }

    private void agregarConsumo() {
        System.out.print("Ingrese numero de identificacion del registrador: ");
        String numeroIdentificacion = scanner.nextLine();
        System.out.print("Ingrese fecha y hora del consumo (YYYY-MM-DDTHH:MM): ");
        String fechaHoraStr = scanner.nextLine();
        LocalDateTime fechaHora = LocalDateTime.parse(fechaHoraStr);
        System.out.print("Ingrese cantidad de kWh consumidos: ");
        double kWh = scanner.nextDouble();
        scanner.nextLine(); // Limpiar el buffer del scanner

        Consumo consumo = new Consumo(fechaHora, kWh);
        boolean exito = controlador.agregarConsumoRegistrador(numeroIdentificacion, consumo);
        if (exito) {
            System.out.println("Consumo agregado exitosamente.");
        } else {
            System.out.println("Error al agregar consumo. Registrador no encontrado.");
        }
    }

    private void verConsumosRegistrador() {
        System.out.print("Ingrese numero de identificacion del registrador: ");
        String numeroIdentificacion = scanner.nextLine();
        List<Consumo> consumos = controlador.obtenerConsumosRegistrador(numeroIdentificacion);
        
        if (consumos != null && !consumos.isEmpty()) {
            System.out.println("Consumos del registrador " + numeroIdentificacion + ":");
            for (Consumo consumo : consumos) {
                System.out.println(consumo);
            }
        } else {
            System.out.println("No se encontraron consumos para el registrador especificado.");
        }
    }

}







package view;

import controller.Controlador; // Importa la clase Controlador
import java.util.Scanner; // Para leer la entrada del usuario

public class Vista {

    private Controlador controlador; // Referencia al controlador
    private Scanner scanner; // Objeto para leer la entrada del usuario
    
    // Constructor de la vista que recibe el controlador
    public Vista(Controlador controlador){
        this.controlador = controlador;
        this.scanner = new Scanner(System.in); // Se inicializa el scanner
    }
    
    public void mMostrarMenuPrincipal(){
        // ... (lógica del menú principal) ...
        // Por ahora, llamamos directamente a crear cliente para probar
        mMostrarMenuCrearCliente();
    }

    // Metodo para solicitar los datos del nuevo cliente al usuario
    
    public void mMostrarMenuCrearCliente(){
        System.out.println("CREAR NUEVO CLIENTE");

        try {System.out.println("Ingrese el numero de identificacion: ");
        long numId = Long.parseLong(scanner.nextLine());
        System.out.println("Ingrese el tipo de identificacion: ");
        String tipoId = scanner.nextLine();
        System.out.println("Ingrese el correo electronico: ");
        String correo = scanner.nextLine();
        System.out.println("Ingrese la direccion: ");
        String direccion = scanner.nextLine();

        // Llamamos al metodo del controlador para crear al cliente
        controlador.mCrearCliente(numId, tipoId, correo, direccion);
        System.out.println("Cliente añadido desde la vista");
        } catch (NumberFormatException e) {
            System.out.println("Error al crear el cliente");
        } catch (Exception e) {
            System.out.println("Error al crear el cliente" + e.getMessage());
        }
        
    }
    
}

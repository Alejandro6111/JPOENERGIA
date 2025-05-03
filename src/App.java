import controller.Controlador;
import view.Vista;
public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hola mundo");
        System.out.println("Programa en Java");
        System.out.println("Iniciando aplicacion.................");

        // 1 Crear la instancia del controlador 
        Controlador miControlador = new Controlador();

        // 2 Crear la instancia de la vista
        Vista miVista = new Vista(miControlador);

        // 3 Iniciar la aplicacion mostrando el menu principal
        miVista.mostrarMenuPrincipal();

        System.out.println("Aplicacion finalizada");
    }
}

package main;

import view.Vista;

/**
 * Esta es la clase que da inicio a toda la aplicación de gestión de consumo eléctrico.
 * Su única tarea es crear la "Vista" (la interfaz con el usuario) y ponerla en marcha.
 */
public class Main {

    /**
     * El método que se ejecuta cuando arranca el programa.
     * @param args Argumentos que se le pueden pasar al programa desde la línea de comandos (no los usamos aquí).
     */
    public static void main(String[] args) {
        System.out.println("Iniciando la Aplicación de Gestión de Energía Eléctrica...");
        System.out.println("Bienvenido al sistema para Clientes No Regulados.\n");
        
        // Creamos la Vista, que es la que interactuará con el usuario.
        Vista interfazDeUsuario = new Vista(); 
        // Le decimos a la Vista que comience a mostrar el menú y a funcionar.
        interfazDeUsuario.mIniciar(); 
        
        System.out.println("\nGracias por usar la aplicación. ¡Que tengas un buen día!");
    }
}
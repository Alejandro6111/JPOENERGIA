package main;

import view.Vista;

public class Main {
    public static void main(String[] args) {
        // Aquí puedes iniciar tu aplicación, crear instancias de Controlador, Cliente, Registrador, etc.
        System.out.println("Aplicación iniciada. ¡Bienvenido!");
        
        Vista vista = new Vista();
        vista.iniciar(); // Inicia la vista, que a su vez maneja la interacción con el usuario

        
    }
}
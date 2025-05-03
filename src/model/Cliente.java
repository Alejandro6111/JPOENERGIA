package model; // Indica que esta clase pertenece al paquete model

import java.util.ArrayList; // Necesitamos importar la clase ArrayList para la lista de registradores
import java.util.List; // Importar List tambien

public class Cliente {

    // Atributos del cliente 
    // Numero unico, tipo id, correo, direccion
    private long numeroIdentificacion; // Usamos long para numeros de identidicacion potencialmente grandes
    private String tipoIdentificacion;
    private String correoElectronico;
    private String direccionFisica;

    // Un cliente debe tener almenos un registrador
    // Uso una lista para almecenar los registradores asociados al cliente
    private List<Registrador> registradores;

    // Constructor 
    // Un metodo especial para crear nuebos objetos Cliente 
    // Inicializamos los atributos basicos y la lista de registradores

    public Cliente(long numeroIdentificacion, String tipoIdentificacion, String correoElectronico, String direccionFisica){
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoIdentificacion = tipoIdentificacion;
        this.correoElectronico = correoElectronico;
        this.direccionFisica = direccionFisica;
        this.registradores = new ArrayList<>();
    }

    
}

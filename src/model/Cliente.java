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
        this.registradores = new ArrayList<>(); // Creamos una lista vacia al crear el cliente
    }

    // Getters y Setters


    // Getter para numero de identificacion
    public long mGetNumeroIdentificacion(){
        return numeroIdentificacion;
    }

    // Getter para tipo de identificacion 
    public String mGetTipoIdentificacion(){
        return tipoIdentificacion;
    }

    // Setter para tipo de identificacion 
    public void mSetTipoIdentificacion(String tipoIdentificacion){
        this.tipoIdentificacion = tipoIdentificacion;
    }

    // Getter para correo electronico 
    public String mGetCorreoElectronico(){
        return correoElectronico;
    }

    // Setter para correo electronico
    public void mSetCorreoElectronico(String correoElectronico){
        this.correoElectronico = correoElectronico;
    }

    // Getter para direccion fisica
    public String mGetDireccionFisica(){
        return direccionFisica;
    }

    // Setter para direccion fisica 
    public void mSetDireccionFisica(String direccionFisica){
        this.direccionFisica = direccionFisica;
    }

    // Getter para la lista de registradores 
    public List<Registrador> mGetRegistradores(){
        return registradores;
    }

    // Metodo para añadadir un registrador a la lista del cliente
    public void mAgregarRegistrador(Registrador registrador){
        this.registradores.add(registrador);
    }

    @Override // Sobrescribe toStrig para mostrar informacion util sobre el cliente
    public String toString(){
        return "Cliente ID: " + numeroIdentificacion + 
        ", Tipo de identificacion: " + tipoIdentificacion + 
        ", Correo electronico: " + correoElectronico +
        ", Direccion: " + direccionFisica + 
        ", Registradores: " + registradores.size(); // se muestra cuantos registradores tiene
    }




    
}

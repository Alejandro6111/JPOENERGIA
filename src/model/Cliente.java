package model; // Indica que esta clase pertenece al paquete model

import java.time.YearMonth;
import java.util.ArrayList; // Necesitamos importar la clase ArrayList para la lista de registradores
import java.util.List; // Importar List tambien
import java.util.Objects; // Importamos Objects para comparar objetos si es necesario

/**
 * Clase que representa a un Cliente no regulado de energía.
 */
public class Cliente {

    /** Número de identificación único del cliente */
    private final String numeroIdentificacion; // Usamos long para numeros de identidicacion potencialmente grandes
    private String tipoIdentificacion;
    private String correoElectronico;
    private String direccionFisica;

      /** Lista de registradores asociados al cliente */
    private List<Registrador> registradores;

    /**
     * Constructor del cliente.
     * @param numeroIdentificacion Número de identificación.
     * @param tipoIdentificacion Tipo de documento.
     * @param correoElectronico Correo electrónico.
     * @param direccionFisica Dirección física.
     */
    public Cliente(String numeroIdentificacion, String tipoIdentificacion, String correoElectronico, String direccionFisica){
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoIdentificacion = tipoIdentificacion;
        this.correoElectronico = correoElectronico;
        this.direccionFisica = direccionFisica;
        this.registradores = new ArrayList<>(); // Creamos una lista vacia al crear el cliente
    }

    // Getters y Setters


    // Getter para numero de identificacion
    public String mGetNumeroIdentificacion(){
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

   /**
     * Agrega un registrador si no existe aún.
     * @param registrador Registrador a agregar.
     */
    public void mAgregarRegistrador(Registrador registrador){
        if (registrador != null && !this.registradores.contains(registrador)) {
            this.registradores.add(registrador); // Añade el registrador a la lista si no es nulo y no esta repetido
        } else {
            System.out.println("Registrador ya existe o es nulo, no se puede agregar.");
            
        }
    }

   /**
     * Elimina un registrador por su número de identificación.
     * @param idRegistrador Número de identificación del registrador.
     */
    public void mEliminarRegistrador(String idRegistrador){
        this.registradores.removeIf(r -> String.valueOf(r.getNumeroIdentificacion()).equals(idRegistrador)); // Elimina el registrador de la lista
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Compara si son el mismo objeto
        if (!(o instanceof Cliente)) return false; // Comprueba si es una instancia de Cliente
        Cliente cliente = (Cliente) o; // Convierte el objeto a Cliente
        return Objects.equals(numeroIdentificacion, cliente.numeroIdentificacion); // Compara el numero de identificacion
    }

    @Override // Sobrescribe toStrig para mostrar informacion util sobre el cliente
    public String toString(){
        return "Cliente ID: " + numeroIdentificacion + 
        ", Tipo de identificacion: " + tipoIdentificacion + 
        ", Correo electronico: " + correoElectronico +
        ", Direccion: " + direccionFisica + 
        ", Registradores: " + registradores.size(); // se muestra cuantos registradores tiene
    }
    
    /**
     * Método pendiente de implementar para cargar consumos mensuales.
     * @param of Año y mes del consumo.
     */
    public void cargarConsumoMensual(YearMonth of) {
        for (Registrador r : registradores) {
            r.cargarConsumoMensual(of); // Llama al metodo de cada registrador para cargar el consumo mensual
        }
    }

}

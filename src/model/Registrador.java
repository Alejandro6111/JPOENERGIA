package model; // Pertenece al mismo paquete que Cliente

import java.util.ArrayList; // Para la futura lista de consumos
import java.util.List; // Para la futura lista de consumos

public class Registrador {
    
    // Atributos del registrador 
    // Numero de identiciacion, direcciom y ciudad
    private long numeroIdentificacion; 
    private String direccion;
    private String ciudad;

    // Un registrador tendra asociados muchos registros de consumos (uno por cada hora) Podemos preparar una lista para almacenarlos, aunque aun nocreemos la clase Consumo
    private List<Consumo> consumos; // Marcara error porque no hemos creado la clase Consumo

    // Constructor 
    public Registrador(long numeroIdentificacion, String direccion, String ciudad){
        this.numeroIdentificacion = numeroIdentificacion;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.consumos = new ArrayList<>(); // Inicializamos la lista consumos vacia
    }

    // Getters y Setters

    public long mGetNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    // Getter para direccion 
    public String mGetDireccion() {
        return direccion;
    }

    // Setter para direccion
    public void mSetDireccion(String direccion) {
        this.direccion = direccion;
    }

    // Getter para ciudad
    public String mGetCiudad() {
        return ciudad;
    }

    // Setter para ciudad
    public void mSetCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    // Getter para la lista de consumos
    public List<Consumo> mGetConsumos() {
        return consumos;
    }

    // Metodo para añadir un consumo a la lista del registrador
    public void mAgregarCosumo(Consumo consumo){
        this.consumos.add(consumo);
    }

}

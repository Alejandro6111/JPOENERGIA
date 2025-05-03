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
    private List<Consumo> consumos;


}

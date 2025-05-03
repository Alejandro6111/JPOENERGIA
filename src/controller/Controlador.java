package controller;

import model.Cliente; // Importa la clase Cliente
import model.Registrador; // Puede que se necesite
import java.util.ArrayList;
import java.util.List;

public class Controlador {
  
    // Lista para almacenar todos los clientes creador en la aplicacion 
    private List<Cliente> listaClientes = new ArrayList<>();

    // Constructor del controlador
    public Controlador(){
        this.listaClientes = new ArrayList<>();
    }

    // Metodo para crear un nuevo cliente (Recibe datos desde la vista)

    public void mCrearCliente(long numeroId, String tipoId, String correo, String direccion){
        // Creamos el nuevo objeto Cliente usando el constructor de la clase cliente
        Cliente nuevoCliente = new Cliente(numeroId, tipoId, correo, direccion);

        // Agregamos el cliente recien creado a nuestra lista
        this.listaClientes.add(nuevoCliente);
        //System.out.println("Cliente creado exitosamente" + nuevoCliente.mGetNumeroIdentificacion());
    }

    // Metodo para obtener la lista de clientes
    public List<Cliente> mGetListaClientes(){
        return this.listaClientes;
    }











}

package controller;

import model.Cliente; // Importa la clase Cliente
import model.Registrador; // Importa la clase Registrador
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


    public boolean mCrearRegistradorParaCliente(long idCliente, long idRegistrador, String direccion, String ciudad){
        // Buscar al cliente por su ID 
        Cliente clienteEncontrado = mBuscarClientePorId(idCliente);
        if(clienteEncontrado != null){
            // Verifica su ya existe un registrador con ese ID

            //Crear el nuevo registrador
            Registrador nuevoRegistrador = new Registrador(idRegistrador, direccion, ciudad);
            
            // Añadir el registrador a la lista del cliente encontrado
            clienteEncontrado.mAgregarRegistrador(nuevoRegistrador);

            System.out.println("Registrador " + idRegistrador + "Añadido al cliente" + idCliente + "en el controlador"); //Mensaje de depuraciob 
            return true;

        } else {
            // No se encontro el cliente con ese ID
            return false;
        }
    }

    // Metodo auxiliar/privado: Busca un cliente por ID en la lista
    private Cliente mBuscarClientePorId(long idCliente){
        for (Cliente cliente : listaClientes){
            if (cliente.mGetNumeroIdentificacion() == idCliente){
                return cliente; // Devuelve el cliente si lo encuentra
            }
        }
        return null; // Devuelve null su no lo encuentra
    }





}

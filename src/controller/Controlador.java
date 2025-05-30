package controller;

import model.Registrador;
import model.Cliente; // Importa la clase Cliente
import model.Consumo; // Importa la clase Consumo
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;
import java.time.YearMonth;

/**
 * Clase Controlador que gestiona la lógica del negocio relacionada con
 * clientes, registradores y consumos eléctricos.
 */
public class Controlador {
  
    // Lista para almacenar todos los clientes creador en la aplicacion 
    private List<Cliente> Clientes = new ArrayList<>();

    /**
     * Busca un cliente por su número de identificación.
     *
     * @param numeroIdentificacion Número de identificación del cliente.
     * @return Cliente encontrado o null si no existe.
     */
    private Cliente busCliente(String numeroIdentificacion) {
        for (Cliente cliente : Clientes) {
            if (cliente.mGetNumeroIdentificacion().equals(numeroIdentificacion)) {
                return cliente; // Retorna el cliente si se encuentra
            }
        }
        return null; // Retorna null si no se encuentra el cliente
    }

/**
     * Crea un nuevo cliente y lo agrega a la lista.
     *
     * @param numeroIdentificacion Número de identificación.
     * @param tipoIdentificacion Tipo de identificación.
     * @param correoElectronico Correo electrónico del cliente.
     * @param direccionFisica Dirección física del cliente.
     * @return true si el cliente se crea correctamente.
     */
    public boolean crearCliente(String numeroIdentificacion, String tipoIdentificacion, String correoElectronico, String direccionFisica) {
        Cliente nuevoCliente = new Cliente(numeroIdentificacion, tipoIdentificacion, correoElectronico, direccionFisica);
        Clientes.add(nuevoCliente); // Añade el cliente a la lista
        return true; // Retorna el cliente creado
    }

  /**
     * Edita los datos de un cliente existente.
     *
     * @param numeroIdentificacion Número de identificación del cliente.
     * @param tipoIdentificacion Nuevo tipo de identificación.
     * @param correoElectronico Nuevo correo electrónico.
     * @param direccionFisica Nueva dirección física.
     * @return Cliente actualizado o null si no se encuentra.
     */
    public Cliente editarCliente(String numeroIdentificacion, String tipoIdentificacion, String correoElectronico, String direccionFisica) {
        Cliente cliente = busCliente(numeroIdentificacion);
        if (cliente != null) {
            cliente.mSetTipoIdentificacion(tipoIdentificacion);
            cliente.mSetCorreoElectronico(correoElectronico);
            cliente.mSetDireccionFisica(direccionFisica);
            return cliente; // Retorna el cliente editado
        }
        return null; // Retorna null si no se encuentra el cliente
    }

   /**
     * Crea un registrador y lo asocia a un cliente existente.
     *
     * @param numeroIdentificacion Número de identificación del registrador.
     * @param direccion Dirección del registrador.
     * @param ciudad Ciudad del registrador.
     * @param numeroCliente Número de identificación del cliente.
     * @return Registrador creado o null si el cliente no se encuentra.
     */
    public Registrador crearRegistrador(String numeroIdentificacion, String direccion, String ciudad, String numeroCliente) {
        Cliente cliente = busCliente(numeroCliente);
        if (cliente != null) {
            Registrador nuevoRegistrador = new Registrador(numeroIdentificacion, direccion, ciudad);
            cliente.mAgregarRegistrador(nuevoRegistrador); // Añade el registrador al cliente
            return nuevoRegistrador; // Retorna el registrador creado
        }
        return null; // Retorna null si no se encuentra el cliente
    }

  /**
     * Edita los datos de un registrador existente.
     *
     * @param numeroIdentificacion Número de identificación del registrador.
     * @param direccion Nueva dirección.
     * @param ciudad Nueva ciudad.
     * @param numeroCliente Número de cliente asociado.
     * @return Registrador actualizado o null si no se encuentra.
     */
    public Registrador editarRegistrador(String numeroIdentificacion, String direccion, String ciudad, String numeroCliente) {
        Cliente cliente = busCliente(numeroCliente);
        if (cliente != null) {
            for (Registrador registrador : cliente.mGetRegistradores()) {
                if (registrador.getNumeroIdentificacion().equals(numeroIdentificacion)) {
                    registrador.setDireccion(direccion);
                    registrador.setCiudad(ciudad);
                    return registrador; // Retorna el registrador editado
                }
            }
        }
        return null; // Retorna null si no se encuentra el registrador o el cliente
    }

    
    /**
     * Carga el consumo de energía mensual para todos los clientes.
     *
     * @param año Año del consumo.
     * @param mes Mes del consumo.
     */
    public void cargarConsumoTodos(int año, int mes){
        for(Cliente c : Clientes){
            c.cargarConsumoMensual(YearMonth.of(año, mes)); // Llama al metodo de cada cliente para cargar el consumo del mes especificado
        }
    }

      /**
     * Agrega un registro de consumo a todos los registradores de un cliente.
     *
     * @param numeroCliente Número de cliente.
     * @param fechaHora Fecha y hora del consumo.
     * @param kWh Cantidad de consumo en kWh.
     * @return true si se agrega exitosamente.
     */
    public boolean cargarConsumoCliente(String numeroCliente, LocalDateTime fechaHora, double kWh) {
        Cliente cliente = busCliente(numeroCliente);
        if (cliente != null) {
            for (Registrador registrador : cliente.mGetRegistradores()) {
                YearMonth mes = YearMonth.from(fechaHora);
                registrador.agregarConsumo(mes, new Consumo(fechaHora, kWh)); // Añade el consumo al registrador
            }
            return true; // Retorna true si se carga el consumo correctamente
        }
        return false; // Retorna false si no se encuentra el cliente
    }

/**
     * Modifica un valor de consumo en una fecha y hora específica.
     *
     * @param numeroCliente Número del cliente.
     * @param fechaHora Fecha y hora específica.
     * @param nuevoKwH Nuevo valor en kWh.
     * @return true si se modifica exitosamente.
     */
    public boolean cambiarConsumoCliente(String numeroCliente, LocalDateTime fechaHora, double nuevoKwH) {
        Cliente cliente = busCliente(numeroCliente);
        if (cliente != null) {
            for (Registrador registrador : cliente.mGetRegistradores()) {
                if (registrador.modificarConsumo(fechaHora, nuevoKwH)) { // Modifica el consumo del registrador
                    return true; // Retorna true si se modifica el consumo correctamente
                }
            }
        }
        return false; // Retorna false si no se encuentra el cliente o no se modifica el consumo
    }

    /**
     * Retorna la lista completa de clientes.
     */
    public List<Cliente> getClientes() {
        return Clientes; // Retorna la lista de clientes
    }

     /**
     * Retorna la lista de clientes (mismo propósito que getClientes).
     */
    public List<Cliente> mGetListaClientes() {
        return Clientes; // Retorna la lista de clientes
    }

    /**
     * Retorna un cliente por su número de identificación.
     */
    public Cliente mGetClientePorNumeroIdentificacion(String numeroIdentificacion) {
        return busCliente(numeroIdentificacion); // Retorna el cliente encontrado o null si no existe
    }

    /**
     * Busca un registrador asociado a un cliente por su número de identificación.
     */
    public Registrador mGetRegistradorPorNumeroIdentificacion(String numeroIdentificacion, String numeroCliente) {
        Cliente cliente = busCliente(numeroCliente);
        if (cliente != null) {
            for (Registrador registrador : cliente.mGetRegistradores()) {
                if (registrador.getNumeroIdentificacion().equals(numeroIdentificacion)) {
                    return registrador; // Retorna el registrador encontrado
                }
            }
        }
        return null; // Retorna null si no se encuentra el registrador o el cliente
    }

     /**
     * Elimina un cliente por su número de identificación.
     */
    public boolean eliminarCliente(String numeroIdentificacion) {
        Cliente cliente = busCliente(numeroIdentificacion);
        if (cliente != null) {
            Clientes.remove(cliente); // Elimina el cliente de la lista
            return true; // Retorna true si se elimina correctamente
        }
        return false; // Retorna false si no se encuentra el cliente
    }

    public List<Consumo> obtenerConsumosRegistrador(String numeroIdentificacion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerConsumosRegistrador'");
    }

    public boolean agregarConsumoRegistrador(String numeroIdentificacion, Consumo consumo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'agregarConsumoRegistrador'");
    }

}

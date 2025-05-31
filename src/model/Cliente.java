package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Representa a un cliente de servicios de energía.
 * Guarda su información básica y una lista de los medidores (registradores) que tiene asociados.
 * Según las reglas del proyecto, un cliente necesita tener al menos un medidor de energía.
 */
public class Cliente {

    /** Número de identificación único del cliente. Una vez asignado, no cambia. */
    private final String numeroIdentificacion;

    /** Tipo de documento del cliente, por ejemplo: Cédula, NIT, etc. */
    private String tipoIdentificacion;

    /** Correo electrónico de contacto del cliente. */
    private String correoElectronico;

    /** Dirección donde reside o se encuentra el cliente. */
    private String direccionFisica;

    /** Lista de medidores de energía que pertenecen a este cliente. */
    private List<Registrador> registradores;

    /**
     * Crea una nueva instancia de Cliente.
     *
     * @param numeroIdentificacion El número único para identificar al cliente. Es obligatorio.
     * @param tipoIdentificacion El tipo de documento que usa el cliente.
     * @param correoElectronico El email del cliente.
     * @param direccionFisica La dirección postal del cliente.
     */
    public Cliente(String numeroIdentificacion, String tipoIdentificacion,
                   String correoElectronico, String direccionFisica) {
        if (numeroIdentificacion == null || numeroIdentificacion.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de identificación es obligatorio y no puede estar vacío.");
        }
        this.numeroIdentificacion = numeroIdentificacion;
        this.tipoIdentificacion = tipoIdentificacion;
        this.correoElectronico = correoElectronico;
        this.direccionFisica = direccionFisica;
        this.registradores = new ArrayList<>(); // Cada cliente empieza con una lista de medidores vacía.
    }

    // --- Métodos para obtener y modificar la información del cliente ---

    /**
     * Devuelve el número de identificación del cliente.
     * @return El número de identificación.
     */
    public String mGetNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * Devuelve el tipo de documento del cliente.
     * @return El tipo de identificación.
     */
    public String mGetTipoIdentificacion() {
        return tipoIdentificacion;
    }

    /**
     * Permite cambiar el tipo de documento del cliente.
     * @param tipoIdentificacion El nuevo tipo de documento.
     */
    public void mSetTipoIdentificacion(String tipoIdentificacion) {
        this.tipoIdentificacion = tipoIdentificacion;
    }

    /**
     * Devuelve el correo electrónico del cliente.
     * @return El correo electrónico.
     */
    public String mGetCorreoElectronico() {
        return correoElectronico;
    }

    /**
     * Permite cambiar el correo electrónico del cliente.
     * @param correoElectronico El nuevo correo electrónico.
     */
    public void mSetCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    /**
     * Devuelve la dirección física del cliente.
     * @return La dirección física.
     */
    public String mGetDireccionFisica() {
        return direccionFisica;
    }

    /**
     * Permite cambiar la dirección física del cliente.
     * @param direccionFisica La nueva dirección física.
     */
    public void mSetDireccionFisica(String direccionFisica) {
        this.direccionFisica = direccionFisica;
    }

    /**
     * Devuelve una lista con todos los medidores asociados a este cliente.
     * Se entrega una copia de la lista para mantener segura la lista original del cliente.
     * @return Una lista de objetos Registrador.
     */
    public List<Registrador> mGetRegistradores() {
        return new ArrayList<>(registradores); // Se devuelve una copia.
    }

    // --- Métodos para administrar los medidores del cliente ---

    /**
     * Agrega un nuevo medidor a la lista del cliente.
     * No se agregará si el medidor es nulo o si ya existe uno con el mismo número de identificación.
     *
     * @param registrador El medidor que se desea agregar.
     * @return true si se pudo agregar, false si hubo algún problema (nulo, duplicado).
     */
    public boolean mAgregarRegistrador(Registrador registrador) {
        if (registrador == null) {
            System.out.println("Atención: No se puede agregar un medidor nulo.");
            return false;
        }
        // Verifica que no haya ya un medidor con el mismo ID para este cliente.
        for (Registrador r : this.registradores) {
            if (r.mGetNumeroIdentificacion().equals(registrador.mGetNumeroIdentificacion())) {
                System.out.println("Atención: El medidor con ID " + registrador.mGetNumeroIdentificacion() + " ya está asociado a este cliente. No se agregó.");
                return false;
            }
        }
        this.registradores.add(registrador);
        return true;
    }

    /**
     * Elimina un medidor de la lista del cliente, usando su número de identificación.
     *
     * @param idRegistrador El número de identificación del medidor a quitar.
     * @return true si se encontró y eliminó el medidor, false si no.
     */
    public boolean mEliminarRegistrador(String idRegistrador) {
        if (idRegistrador == null) return false;
        return this.registradores.removeIf(r -> r.mGetNumeroIdentificacion().equals(idRegistrador));
    }

    /**
     * Busca un medidor específico dentro de la lista de este cliente.
     * @param idRegistrador El número de identificación del medidor que se busca.
     * @return El objeto Registrador si se encuentra, o null si no existe.
     */
    public Registrador mBuscarRegistrador(String idRegistrador) {
        if (idRegistrador == null) return null;
        for (Registrador r : this.registradores) {
            if (r.mGetNumeroIdentificacion().equals(idRegistrador)) {
                return r;
            }
        }
        return null; // No se encontró el medidor.
    }

    // --- Métodos estándar de Java ---

    /**
     * Compara este cliente con otro objeto para ver si son "iguales".
     * Se considera que dos clientes son iguales si tienen el mismo número de identificación.
     * @param o El otro objeto con el que se quiere comparar.
     * @return true si son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Es el mismo objeto en memoria.
        if (!(o instanceof Cliente)) return false; // No es ni siquiera un Cliente.
        Cliente cliente = (Cliente) o; // Ahora sí, lo trato como Cliente.
        return Objects.equals(numeroIdentificacion, cliente.numeroIdentificacion); // Compara los IDs.
    }

    /**
     * Genera un número (código hash) que representa al cliente.
     * Útil para colecciones que optimizan búsquedas, como HashMaps. Se basa en el ID.
     * @return El código hash del cliente.
     */
    @Override
    public int hashCode() {
        return Objects.hash(numeroIdentificacion);
    }

    /**
     * Devuelve un texto que describe al cliente con su información principal.
     * @return Una cadena de texto con los datos del cliente.
     */
    @Override
    public String toString() {
        return "Cliente {" +
               "ID: '" + numeroIdentificacion + '\'' +
               ", Tipo ID: '" + tipoIdentificacion + '\'' +
               ", Correo: '" + correoElectronico + '\'' +
               ", Dirección: '" + direccionFisica + '\'' +
               ", Cantidad de Medidores: " + registradores.size() +
               '}';
    }
}
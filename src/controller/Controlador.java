package controller;

import model.Cliente;
import model.Consumo;
import model.Registrador;
// No es necesario importar FranjaHoraria aquí si solo se usa dentro de Consumo.mCalcularCosto

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Es el "cerebro" de la aplicación. Conecta la Vista (lo que ve el usuario)
 * con el Modelo (los datos y cómo funcionan). Se encarga de realizar las
 * operaciones que el usuario solicita.
 */
public class Controlador {

    /** Guarda la lista de todos los clientes que se han creado. */
    private List<Cliente> listaClientes;
    /** Se usa para generar números al azar, útil para simular consumos. */
    private final Random generadorAleatorio;

    /**
     * Constructor del Controlador. Prepara la lista de clientes y el generador de números.
     */
    public Controlador() {
        this.listaClientes = new ArrayList<>();
        this.generadorAleatorio = new Random();
    }

    // --- OPERACIONES RELACIONADAS CON CLIENTES ---

    /**
     * Busca un cliente en la lista usando su número de identificación.
     * Es un método privado porque solo lo usa el Controlador internamente.
     *
     * @param numeroIdentificacion El ID del cliente que se quiere encontrar.
     * @return El objeto Cliente si se encuentra, o null si no existe.
     */
    private Cliente mBusCliente(String numeroIdentificacion) {
        if (numeroIdentificacion == null) return null;
        for (Cliente cliente : listaClientes) {
            if (cliente.mGetNumeroIdentificacion().equals(numeroIdentificacion)) {
                return cliente; // ¡Encontrado!
            }
        }
        return null; // No se encontró.
    }

    /**
     * Permite crear un nuevo cliente y añadirlo al sistema.
     * Corresponde al Requisito 1 del proyecto.
     *
     * @param numeroIdentificacion El ID único para el nuevo cliente.
     * @param tipoIdentificacion El tipo de documento (CC, NIT, etc.).
     * @param correoElectronico El email del cliente.
     * @param direccionFisica La dirección del cliente.
     * @return true si se pudo crear el cliente (porque el ID no existía antes),
     * false si ya había un cliente con ese ID.
     */
    public boolean mCrearCliente(String numeroIdentificacion, String tipoIdentificacion,
                                String correoElectronico, String direccionFisica) {
        if (mBusCliente(numeroIdentificacion) != null) {
            return false; // Ya existe un cliente con este ID.
        }
        Cliente nuevoCliente = new Cliente(numeroIdentificacion, tipoIdentificacion,
                                           correoElectronico, direccionFisica);
        listaClientes.add(nuevoCliente);
        return true;
    }

    /**
     * Permite modificar los datos de un cliente que ya existe.
     * No se puede cambiar el número de identificación.
     * Corresponde al Requisito 2 del proyecto.
     *
     * @param numeroIdentificacion El ID del cliente que se quiere editar.
     * @param nuevoTipoIdentificacion El nuevo tipo de documento.
     * @param nuevoCorreoElectronico El nuevo email.
     * @param nuevaDireccionFisica La nueva dirección.
     * @return El objeto Cliente con los datos actualizados si se encontró,
     * o null si el cliente con ese ID no existe.
     */
    public Cliente mEditarCliente(String numeroIdentificacion, String nuevoTipoIdentificacion,
                                 String nuevoCorreoElectronico, String nuevaDireccionFisica) {
        Cliente clienteAEditar = mBusCliente(numeroIdentificacion);
        if (clienteAEditar != null) {
            clienteAEditar.mSetTipoIdentificacion(nuevoTipoIdentificacion);
            clienteAEditar.mSetCorreoElectronico(nuevoCorreoElectronico);
            clienteAEditar.mSetDireccionFisica(nuevaDireccionFisica);
            return clienteAEditar;
        }
        return null; // Cliente no encontrado.
    }

    /**
     * Elimina un cliente del sistema usando su número de identificación.
     * (Esta función no es un requisito principal del PDF, pero es útil).
     *
     * @param numeroIdentificacion El ID del cliente a eliminar.
     * @return true si se eliminó, false si no se encontró.
     */
    public boolean mEliminarCliente(String numeroIdentificacion) {
        Cliente clienteAEliminar = mBusCliente(numeroIdentificacion);
        if (clienteAEliminar != null) {
            listaClientes.remove(clienteAEliminar);
            return true;
        }
        return false;
    }

    /**
     * Devuelve una copia de la lista de todos los clientes registrados.
     * @return Una lista de objetos Cliente.
     */
    public List<Cliente> mGetClientes() {
        return new ArrayList<>(listaClientes); // Se devuelve una copia.
    }

    // --- OPERACIONES RELACIONADAS CON MEDIDORES (REGISTRADORES) ---

    /**
     * Crea un nuevo medidor (registrador) y lo asocia a un cliente existente.
     * Corresponde al Requisito 3 del proyecto.
     *
     * @param idRegistrador El ID único para el nuevo medidor.
     * @param direccion La dirección donde se instala el medidor.
     * @param ciudad La ciudad donde se ubica.
     * @param idCliente El ID del cliente al que pertenecerá este medidor.
     * @return El objeto Registrador que se creó, o null si el cliente no existe
     * o si ese cliente ya tiene un medidor con el mismo ID.
     */
    public Registrador mCrearRegistrador(String idRegistrador, String direccion,
                                         String ciudad, String idCliente) {
        Cliente clientePropietario = mBusCliente(idCliente);
        if (clientePropietario != null) {
            // Verificar que el cliente no tenga ya un registrador con ese ID.
            if (clientePropietario.mBuscarRegistrador(idRegistrador) != null) {
                return null; // Registrador duplicado para este cliente.
            }
            Registrador nuevoRegistrador = new Registrador(idRegistrador, direccion, ciudad);
            clientePropietario.mAgregarRegistrador(nuevoRegistrador);
            return nuevoRegistrador;
        }
        return null; // Cliente no encontrado.
    }

    /**
     * Modifica los datos (dirección, ciudad) de un medidor que ya existe.
     * No se puede cambiar el ID del medidor.
     * Corresponde al Requisito 4 del proyecto.
     *
     * @param idRegistrador El ID del medidor que se quiere editar.
     * @param nuevaDireccion La nueva dirección.
     * @param nuevaCiudad La nueva ciudad.
     * @param idCliente El ID del cliente dueño del medidor.
     * @return El objeto Registrador con los datos actualizados,
     * o null si no se encuentra el cliente o el medidor.
     */
    public Registrador mEditarRegistrador(String idRegistrador, String nuevaDireccion,
                                         String nuevaCiudad, String idCliente) {
        Cliente clientePropietario = mBusCliente(idCliente);
        if (clientePropietario != null) {
            Registrador registradorAEditar = clientePropietario.mBuscarRegistrador(idRegistrador);
            if (registradorAEditar != null) {
                registradorAEditar.mSetDireccion(nuevaDireccion);
                registradorAEditar.mSetCiudad(nuevaCiudad);
                return registradorAEditar;
            }
        }
        return null; // No se encontró el cliente o el medidor.
    }

    /**
     * Elimina un medidor asociado a un cliente.
     * (No es un requisito principal del PDF, pero es útil).
     *
     * @param idRegistrador El ID del medidor a eliminar.
     * @param idCliente El ID del cliente dueño.
     * @return true si se eliminó, false si no se encontró.
     */
    public boolean mEliminarRegistrador(String idRegistrador, String idCliente) {
        Cliente clientePropietario = mBusCliente(idCliente);
        if (clientePropietario != null) {
            return clientePropietario.mEliminarRegistrador(idRegistrador);
        }
        return false;
    }

     /**
     * Busca un medidor (registrador) específico que pertenece a un cliente.
     * @param idCliente El ID del cliente.
     * @param idRegistrador El ID del medidor.
     * @return El objeto Registrador si se encuentra, sino null.
     */
    public Registrador mGetRegistradorDeCliente(String idCliente, String idRegistrador) {
        Cliente cliente = mBusCliente(idCliente);
        if (cliente != null) {
            return cliente.mBuscarRegistrador(idRegistrador);
        }
        return null;
    }


    // --- OPERACIONES RELACIONADAS CON CONSUMOS ---

    /**
     * Genera un valor de consumo (kWh) al azar, siguiendo las reglas de
     * las franjas horarias del proyecto para simular datos realistas.
     * - Franja 1 (00-06h): Consumo entre 100 y 300 kWh.
     * - Franja 2 (07-17h): Consumo entre un poco más de 300 y 600 kWh.
     * - Franja 3 (18-23h): Consumo entre un poco más de 600 y casi 1000 kWh.
     *
     * @param hora La hora del día (0 a 23).
     * @return Un valor de kWh simulado.
     */
    private double mGenerarConsumoAleatorioSegunFranjaPDF(int hora) {
        if (hora >= 0 && hora <= 6) { // Franja 1
            return 100 + generadorAleatorio.nextDouble() * (300 - 100); // Entre 100 y 300
        } else if (hora >= 7 && hora <= 17) { // Franja 2
            return 300.01 + generadorAleatorio.nextDouble() * (600 - 300.01); // Entre >300 y 600
        } else if (hora >= 18 && hora <= 23) { // Franja 3
            return 600.01 + generadorAleatorio.nextDouble() * (999.99 - 600.01); // Entre >600 y <1000
        }
        return 0; // Si la hora no corresponde a ninguna franja, devuelve 0.
    }

    /**
     * Simula y carga los consumos hora por hora para todos los medidores
     * de todos los clientes, para un mes y año específicos.
     * Corresponde al Requisito 5 del proyecto.
     *
     * @param anio El año para el que se simulan los consumos.
     * @param mes El mes (1 a 12) para el que se simulan los consumos.
     */
    public void mCargarConsumosAutomaticosTodosClientes(int anio, int mes) {
        YearMonth infoMesAnio = YearMonth.of(anio, mes);
        int numDiasDelMes = infoMesAnio.lengthOfMonth();

        for (Cliente cliente : listaClientes) {
            for (Registrador reg : cliente.mGetRegistradores()) {
                reg.mInicializarConsumos(mes, anio); // Prepara la matriz del medidor para el mes/año.
                for (int dia = 1; dia <= numDiasDelMes; dia++) {
                    for (int hora = 0; hora < 24; hora++) {
                        double valorAleatorio = mGenerarConsumoAleatorioSegunFranjaPDF(hora);
                        reg.mSetConsumoEn(dia, hora, valorAleatorio);
                    }
                }
            }
        }
    }

    /**
     * Simula y carga los consumos hora por hora para todos los medidores
     * de un cliente específico, para un mes y año dados.
     * Corresponde al Requisito 6 del proyecto.
     *
     * @param idCliente El ID del cliente.
     * @param anio El año para la simulación.
     * @param mes El mes (1 a 12) para la simulación.
     * @return true si se encontró el cliente y se cargaron los consumos,
     * false si el cliente no existe.
     */
    public boolean mCargarConsumosAutomaticosUnCliente(String idCliente, int anio, int mes) {
        Cliente cliente = mBusCliente(idCliente);
        if (cliente == null) {
            return false; // Cliente no encontrado.
        }

        YearMonth infoMesAnio = YearMonth.of(anio, mes);
        int numDiasDelMes = infoMesAnio.lengthOfMonth();

        for (Registrador reg : cliente.mGetRegistradores()) {
            reg.mInicializarConsumos(mes, anio); // Prepara la matriz del medidor.
            for (int dia = 1; dia <= numDiasDelMes; dia++) {
                for (int hora = 0; hora < 24; hora++) {
                    double valorAleatorio = mGenerarConsumoAleatorioSegunFranjaPDF(hora);
                    reg.mSetConsumoEn(dia, hora, valorAleatorio);
                }
            }
        }
        return true;
    }

    /**
     * Permite cambiar el valor de consumo de una hora específica, para un día, mes,
     * medidor y cliente determinados.
     * Corresponde al Requisito 7 del proyecto.
     *
     * @param idCliente El ID del cliente.
     * @param idRegistrador El ID del medidor.
     * @param anio El año del consumo a cambiar.
     * @param mes El mes (1-12) del consumo a cambiar.
     * @param dia El día (1 al último del mes) del consumo.
     * @param hora La hora (0-23) del consumo.
     * @param nuevoKWh El nuevo valor de consumo en kWh.
     * @return true si el cambio fue exitoso. False si no se encontró el cliente/medidor,
     * o si los datos del medidor no estaban preparados para ese mes/año, o datos inválidos.
     */
    public boolean mCambiarConsumoHoraEspecifica(String idCliente, String idRegistrador,
                                                int anio, int mes, int dia, int hora, double nuevoKWh) {
        Cliente cliente = mBusCliente(idCliente);
        if (cliente == null) return false;

        Registrador registrador = cliente.mBuscarRegistrador(idRegistrador);
        if (registrador == null) return false;

        // Verifica si los consumos del medidor son del mes y año correctos.
        // Si no, los inicializa para poder registrar el nuevo consumo.
        if (registrador.mGetAnioActualConsumos() != anio || registrador.mGetMesActualConsumos() != mes) {
             registrador.mInicializarConsumos(mes, anio); // Prepara con ceros si es un mes nuevo.
        }
        
        try {
            registrador.mSetConsumoEn(dia, hora, nuevoKWh); // Intenta guardar el nuevo valor.
            return true;
        } catch (IllegalStateException | IllegalArgumentException e) {
            // Esto puede pasar si, por ejemplo, el día o la hora son incorrectos
            // después de la inicialización, o si hubo otro problema.
            System.err.println("Error al intentar cambiar el consumo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Agrega un nuevo registro de consumo (o modifica uno existente si es para la misma hora)
     * a un medidor específico. Usado por la Vista.
     *
     * @param idCliente El ID del cliente.
     * @param idRegistrador El ID del medidor.
     * @param consumo El objeto Consumo que tiene la fecha, hora y kWh.
     * @return true si se agregó/modificó correctamente, false si no.
     */
    public boolean mAgregarConsumoRegistrador(String idCliente, String idRegistrador, Consumo consumo) {
        if (consumo == null) return false;
        LocalDateTime fechaHoraDelConsumo = consumo.mGetFechaHora();
        // Llama al método más detallado para hacer el cambio.
        return mCambiarConsumoHoraEspecifica(
                idCliente,
                idRegistrador,
                fechaHoraDelConsumo.getYear(),
                fechaHoraDelConsumo.getMonthValue(),
                fechaHoraDelConsumo.getDayOfMonth(),
                fechaHoraDelConsumo.getHour(),
                consumo.mGetKWh()
        );
    }

    /**
     * Obtiene una lista de todos los consumos (hora por hora) de un medidor
     * específico, para un mes y año dados. Usado por la Vista.
     *
     * @param idCliente El ID del cliente.
     * @param idRegistrador El ID del medidor.
     * @param anio El año de los consumos deseados.
     * @param mes El mes (1-12) de los consumos.
     * @return Una lista de objetos Consumo. Estará vacía si no hay datos.
     */
    public List<Consumo> mObtenerConsumosRegistrador(String idCliente, String idRegistrador, int anio, int mes) {
        List<Consumo> listaDeConsumos = new ArrayList<>();
        Cliente cliente = mBusCliente(idCliente);
        if (cliente == null) return listaDeConsumos; // Cliente no existe.

        Registrador registrador = cliente.mBuscarRegistrador(idRegistrador);
        if (registrador == null) return listaDeConsumos; // Medidor no existe.

        // Verifica que los datos cargados en el medidor sean del mes y año solicitados.
        if (registrador.mGetAnioActualConsumos() != anio || registrador.mGetMesActualConsumos() != mes) {
            // Si no coinciden, significa que los datos para este periodo no están cargados o no son los actuales.
            return listaDeConsumos; // Devuelve lista vacía.
        }

        double[][] consumosGuardados = registrador.mGetConsumosMensuales();
        if (consumosGuardados == null) return listaDeConsumos; // No hay matriz de consumos.

        // Recorre la matriz de consumos y crea objetos Consumo para cada uno.
        for (int diaIndice = 0; diaIndice < consumosGuardados.length; diaIndice++) {
            for (int hora = 0; hora < consumosGuardados[diaIndice].length; hora++) {
                double kWh = consumosGuardados[diaIndice][hora];
                // Crea la fecha y hora exacta para este consumo.
                LocalDateTime fechaHoraConsumo = LocalDateTime.of(anio, mes, diaIndice + 1, hora, 0);
                listaDeConsumos.add(new Consumo(fechaHoraConsumo, kWh));
            }
        }
        return listaDeConsumos;
    }

    // --- REQUISITOS DEL PDF: DEL 8 AL 13 ---

    /**
     * Genera el contenido de una factura (como texto simple) para un cliente,
     * correspondiente a un mes y año específicos.
     * Corresponde al Requisito 8 del proyecto.
     *
     * @param idCliente El ID del cliente.
     * @param anio El año de la factura.
     * @param mes El mes (1-12) de la factura.
     * @return Un String con el texto de la factura, o un mensaje de error.
     */
    public String mGenerarFacturaTextoCliente(String idCliente, int anio, int mes) {
        Cliente cliente = mBusCliente(idCliente);
        if (cliente == null) return "Error: Cliente con ID " + idCliente + " no encontrado.";

        StringBuilder factura = new StringBuilder();
        factura.append("========================================\n");
        factura.append("         FACTURA DE CONSUMO ELÉCTRICO\n");
        factura.append("========================================\n");
        factura.append("Cliente: ").append(cliente.mGetNumeroIdentificacion()).append(" (").append(cliente.mGetTipoIdentificacion()).append(")\n");
        factura.append("Correo: ").append(cliente.mGetCorreoElectronico()).append("\n");
        factura.append("Dirección: ").append(cliente.mGetDireccionFisica()).append("\n");
        factura.append("Periodo Facturado: ").append(String.format("%02d/%d", mes, anio)).append("\n");
        factura.append("----------------------------------------\n");
        factura.append("Detalle de Consumos por Medidor:\n");

        double totalKWhGeneralCliente = 0;
        double valorTotalFacturaCliente = 0;

        if (cliente.mGetRegistradores().isEmpty()){
            factura.append("\n  ** Este cliente no tiene medidores de energía asociados. **\n");
        }

        for (Registrador registrador : cliente.mGetRegistradores()) {
            factura.append("\n  Medidor ID: ").append(registrador.mGetNumeroIdentificacion()).append("\n");
            factura.append("  Ubicación: ").append(registrador.mGetDireccion()).append(", ").append(registrador.mGetCiudad()).append("\n");

            // Verifica si los datos de este medidor corresponden al periodo de la factura.
            if (registrador.mGetAnioActualConsumos() != anio || registrador.mGetMesActualConsumos() != mes) {
                factura.append("    - Consumos para el periodo ").append(mes).append("/").append(anio)
                       .append(" no están cargados actualmente para este medidor.\n");
                continue; // Pasa al siguiente medidor.
            }

            double[][] consumosDelMes = registrador.mGetConsumosMensuales();
            if (consumosDelMes == null) {
                 factura.append("    - No hay datos de consumo disponibles para este medidor en el periodo.\n");
                 continue;
            }

            double totalKWhDelRegistrador = 0;
            double valorTotalDelRegistrador = 0;

            // Calcula el consumo y costo para este medidor.
            for (int d = 0; d < consumosDelMes.length; d++) { // d es el índice del día (0 para día 1)
                for (int h = 0; h < consumosDelMes[d].length; h++) { // h es la hora (0-23)
                    double kWhEnLaHora = consumosDelMes[d][h];
                    if (kWhEnLaHora > 0) { // Solo procesa si hubo consumo.
                        totalKWhDelRegistrador += kWhEnLaHora;
                        LocalDateTime fechaHora = LocalDateTime.of(anio, mes, d + 1, h, 0);
                        Consumo consumoIndividual = new Consumo(fechaHora, kWhEnLaHora);
                        valorTotalDelRegistrador += consumoIndividual.mCalcularCosto();
                    }
                }
            }
            factura.append(String.format("    Consumo Total del Medidor: %.2f kWh\n", totalKWhDelRegistrador));
            factura.append(String.format("    Valor Total del Medidor: %.2f COP\n", valorTotalDelRegistrador));
            
            totalKWhGeneralCliente += totalKWhDelRegistrador;
            valorTotalFacturaCliente += valorTotalDelRegistrador;
        }

        factura.append("----------------------------------------\n");
        factura.append(String.format("CONSUMO TOTAL GENERAL DEL CLIENTE: %.2f kWh\n", totalKWhGeneralCliente));
        factura.append(String.format("VALOR TOTAL A PAGAR POR EL CLIENTE: %.2f COP\n", valorTotalFacturaCliente));
        factura.append("========================================\n");

        return factura.toString();
    }

    /**
     * Encuentra el consumo horario más bajo (en kWh) de un cliente durante un mes y año específicos,
     * considerando todos sus medidores.
     * Corresponde al Requisito 9 del proyecto.
     *
     * @param idCliente El ID del cliente.
     * @param anio El año.
     * @param mes El mes (1-12).
     * @return El valor del consumo mínimo. Si no hay datos, devuelve un valor muy alto (Double.MAX_VALUE).
     */
    public double mHallarConsumoMinimoClienteMes(String idCliente, int anio, int mes) {
        Cliente cliente = mBusCliente(idCliente);
        if (cliente == null) return Double.MAX_VALUE; // Cliente no existe.

        double consumoMinimoGlobal = Double.MAX_VALUE;
        boolean seEncontraronConsumosValidos = false;

        for (Registrador registrador : cliente.mGetRegistradores()) {
            // Verifica si los datos del medidor son del periodo buscado.
            if (registrador.mGetAnioActualConsumos() == anio && registrador.mGetMesActualConsumos() == mes) {
                double[][] consumosDelMes = registrador.mGetConsumosMensuales();
                if (consumosDelMes != null) {
                    for (int d = 0; d < consumosDelMes.length; d++) {
                        for (int h = 0; h < consumosDelMes[d].length; h++) {
                            if (consumosDelMes[d][h] < consumoMinimoGlobal) {
                                consumoMinimoGlobal = consumosDelMes[d][h];
                            }
                            seEncontraronConsumosValidos = true;
                        }
                    }
                }
            }
        }
        // Si no se encontró ningún consumo, devuelve MAX_VALUE. Sino, el mínimo encontrado.
        return seEncontraronConsumosValidos ? consumoMinimoGlobal : Double.MAX_VALUE;
    }

    /**
     * Encuentra el consumo horario más alto (en kWh) de un cliente durante un mes y año específicos,
     * considerando todos sus medidores.
     * Corresponde al Requisito 10 del proyecto.
     *
     * @param idCliente El ID del cliente.
     * @param anio El año.
     * @param mes El mes (1-12).
     * @return El valor del consumo máximo. Si no hay datos, devuelve un valor muy bajo (Double.MIN_VALUE).
     */
    public double mHallarConsumoMaximoClienteMes(String idCliente, int anio, int mes) {
        Cliente cliente = mBusCliente(idCliente);
        if (cliente == null) return Double.MIN_VALUE; // Cliente no existe.

        double consumoMaximoGlobal = Double.MIN_VALUE;
        boolean seEncontraronConsumosValidos = false;

        for (Registrador registrador : cliente.mGetRegistradores()) {
            if (registrador.mGetAnioActualConsumos() == anio && registrador.mGetMesActualConsumos() == mes) {
                double[][] consumosDelMes = registrador.mGetConsumosMensuales();
                if (consumosDelMes != null) {
                    for (int d = 0; d < consumosDelMes.length; d++) {
                        for (int h = 0; h < consumosDelMes[d].length; h++) {
                            if (consumosDelMes[d][h] > consumoMaximoGlobal) {
                                consumoMaximoGlobal = consumosDelMes[d][h];
                            }
                            seEncontraronConsumosValidos = true;
                        }
                    }
                }
            }
        }
        return seEncontraronConsumosValidos ? consumoMaximoGlobal : Double.MIN_VALUE;
    }

    /**
     * Calcula el consumo total (en kWh) para un cliente, separado por cada franja horaria,
     * durante un mes y año específicos.
     * Las franjas son: Franja 1 (00-06h), Franja 2 (07-17h), Franja 3 (18-23h).
     * Corresponde al Requisito 11 del proyecto.
     *
     * @param idCliente El ID del cliente.
     * @param anio El año.
     * @param mes El mes (1-12).
     * @return Un array de 3 doubles: [total kWh Franja1, total kWh Franja2, total kWh Franja3].
     * Devuelve null si el cliente no existe o no hay datos para el periodo.
     */
    public double[] mHallarConsumoPorFranjasClienteMes(String idCliente, int anio, int mes) {
        Cliente cliente = mBusCliente(idCliente);
        if (cliente == null) return null;

        double[] consumoTotalPorFranja = new double[3]; // Posición 0 para Franja1, 1 para Franja2, etc.
        boolean hayDatosParaCalcular = false;

        for (Registrador registrador : cliente.mGetRegistradores()) {
            if (registrador.mGetAnioActualConsumos() == anio && registrador.mGetMesActualConsumos() == mes) {
                double[][] consumosDelMes = registrador.mGetConsumosMensuales();
                if (consumosDelMes != null) {
                    hayDatosParaCalcular = true;
                    for (int d = 0; d < consumosDelMes.length; d++) {
                        for (int h = 0; h < consumosDelMes[d].length; h++) {
                            double kWh = consumosDelMes[d][h];
                            if (h >= 0 && h <= 6) consumoTotalPorFranja[0] += kWh;       // Acumula en Franja 1
                            else if (h >= 7 && h <= 17) consumoTotalPorFranja[1] += kWh; // Acumula en Franja 2
                            else if (h >= 18 && h <= 23) consumoTotalPorFranja[2] += kWh; // Acumula en Franja 3
                        }
                    }
                }
            }
        }
        return hayDatosParaCalcular ? consumoTotalPorFranja : null;
    }

    /**
     * Calcula el consumo total (en kWh) para un cliente, separado por cada día del mes,
     * durante un mes y año específicos.
     * Corresponde al Requisito 12 del proyecto.
     *
     * @param idCliente El ID del cliente.
     * @param anio El año.
     * @param mes El mes (1-12).
     * @return Un array de doubles, donde cada posición `i` tiene el consumo total del día `i+1`.
     * El tamaño del array será igual al número de días del mes.
     * Devuelve null si el cliente no existe o no hay datos para el periodo.
     */
    public double[] mHallarConsumoPorDiasClienteMes(String idCliente, int anio, int mes) {
        Cliente cliente = mBusCliente(idCliente);
        if (cliente == null) return null;

        YearMonth infoMesAnio = YearMonth.of(anio, mes);
        int numDiasDelMes = infoMesAnio.lengthOfMonth();
        double[] consumoTotalPorDia = new double[numDiasDelMes]; // Un espacio para cada día.
        boolean hayDatosGenerales = false;

        for (Registrador registrador : cliente.mGetRegistradores()) {
            if (registrador.mGetAnioActualConsumos() == anio && registrador.mGetMesActualConsumos() == mes) {
                double[][] consumosDelMes = registrador.mGetConsumosMensuales();
                // Verifica que la matriz de consumos exista y tenga el número correcto de días.
                if (consumosDelMes != null && consumosDelMes.length == numDiasDelMes) {
                    hayDatosGenerales = true;
                    for (int d = 0; d < numDiasDelMes; d++) { // d es el índice del día (0 para día 1)
                        for (int h = 0; h < consumosDelMes[d].length; h++) { // Recorre las horas de ese día
                            consumoTotalPorDia[d] += consumosDelMes[d][h]; // Acumula el consumo del día 'd'.
                        }
                    }
                }
            }
        }
        return hayDatosGenerales ? consumoTotalPorDia : null;
    }

    /**
     * Calcula el valor total (en COP) de la factura para un cliente,
     * correspondiente a un mes y año seleccionados.
     * Corresponde al Requisito 13 del proyecto.
     *
     * @param idCliente El ID del cliente.
     * @param anio El año.
     * @param mes El mes (1-12).
     * @return El valor total de la factura. Devuelve -1.0 si hay algún error o no hay datos.
     */
    public double mCalcularValorFacturaClienteMes(String idCliente, int anio, int mes) {
        Cliente cliente = mBusCliente(idCliente);
        if (cliente == null) return -1.0; // Cliente no encontrado.

        double valorTotalFactura = 0;
        boolean seConsideraronDatos = false;

        for (Registrador registrador : cliente.mGetRegistradores()) {
            if (registrador.mGetAnioActualConsumos() == anio && registrador.mGetMesActualConsumos() == mes) {
                double[][] consumosDelMes = registrador.mGetConsumosMensuales();
                if (consumosDelMes != null) {
                    seConsideraronDatos = true;
                    for (int d = 0; d < consumosDelMes.length; d++) {
                        for (int h = 0; h < consumosDelMes[d].length; h++) {
                            double kWhEnLaHora = consumosDelMes[d][h];
                            if (kWhEnLaHora > 0) { // Solo si hubo consumo.
                                LocalDateTime fechaHora = LocalDateTime.of(anio, mes, d + 1, h, 0);
                                Consumo consumoIndividual = new Consumo(fechaHora, kWhEnLaHora);
                                valorTotalFactura += consumoIndividual.mCalcularCosto(); // Suma el costo de cada consumo.
                            }
                        }
                    }
                }
            }
        }
        // Si no se procesó ningún dato, devuelve -1.0 para indicar que no se pudo calcular.
        return seConsideraronDatos ? valorTotalFactura : -1.0;
    }
}
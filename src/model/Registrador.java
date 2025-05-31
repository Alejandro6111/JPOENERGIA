package model;

import java.time.YearMonth;

/**
 * Representa un medidor (registrador) de consumo eléctrico.
 * Almacena los consumos de energía hora por hora para un mes específico.
 * Cada medidor tiene un número de identificación, una dirección y una ciudad.
 * Los consumos se guardan en una estructura de días por 24 horas.
 */
public class Registrador {

    /** Número de identificación único del medidor. No cambia una vez asignado. */
    private final String numeroIdentificacion;
    /** Dirección donde está instalado el medidor. */
    private String direccion;
    /** Ciudad donde se ubica el medidor. */
    private String ciudad;

    /**
     * Matriz para guardar los consumos del mes.
     * Las filas representan los días (del 1 al último día del mes).
     * Las columnas representan las 24 horas del día (de 0 a 23).
     * Ejemplo: consumosMensuales[día-1][hora] contendrá los kWh de esa hora y día.
     */
    private double[][] consumosMensuales;

    /** Año para el cual son válidos los datos de consumo actuales en la matriz. */
    private int anioActualConsumos;
    /** Mes (1 a 12) para el cual son válidos los datos de consumo actuales. */
    private int mesActualConsumos;


    /**
     * Crea un nuevo medidor.
     *
     * @param numeroIdentificacion El identificador único para este medidor. Es obligatorio.
     * @param direccion La dirección donde está el medidor.
     * @param ciudad La ciudad donde se localiza.
     */
    public Registrador(String numeroIdentificacion, String direccion, String ciudad) {
        if (numeroIdentificacion == null || numeroIdentificacion.trim().isEmpty()) {
            throw new IllegalArgumentException("El número de identificación del medidor es obligatorio.");
        }
        this.numeroIdentificacion = numeroIdentificacion;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.consumosMensuales = null; // La matriz de consumos se crea después, al indicar el mes/año.
        this.anioActualConsumos = 0; // Se inicializan a 0, indicando que no hay datos cargados.
        this.mesActualConsumos = 0;
    }

    // --- Métodos para obtener y modificar la información del medidor ---

    /**
     * Devuelve el número de identificación del medidor.
     * @return El número de identificación.
     */
    public String mGetNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    /**
     * Devuelve la dirección donde está el medidor.
     * @return La dirección.
     */
    public String mGetDireccion() {
        return direccion;
    }

    /**
     * Permite cambiar la dirección del medidor.
     * @param direccion La nueva dirección.
     */
    public void mSetDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Devuelve la ciudad donde está el medidor.
     * @return La ciudad.
     */
    public String mGetCiudad() {
        return ciudad;
    }

    /**
     * Permite cambiar la ciudad del medidor.
     * @param ciudad La nueva ciudad.
     */
    public void mSetCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Devuelve la matriz completa con los consumos del mes.
     * Puede estar vacía (null) si aún no se han cargado datos para un mes específico.
     *
     * @return Una matriz de doubles (kWh), o null si no hay datos.
     */
    public double[][] mGetConsumosMensuales() {
        return consumosMensuales;
    }

    /**
     * Devuelve el año para el cual están cargados los consumos actuales.
     * @return El año (ej. 2025), o 0 si no se han inicializado.
     */
    public int mGetAnioActualConsumos() {
        return anioActualConsumos;
    }

    /**
     * Devuelve el mes (1-12) para el cual están cargados los consumos actuales.
     * @return El mes, o 0 si no se han inicializado.
     */
    public int mGetMesActualConsumos() {
        return mesActualConsumos;
    }

    // --- Métodos para manejar los datos de consumo ---

    /**
     * Prepara (o reinicia) la estructura para guardar los consumos de un mes y año específicos.
     * Se crea una matriz con el número de días correcto para ese mes, y 24 columnas para las horas.
     * Todos los consumos se ponen en 0.0 inicialmente.
     * También guarda el mes y año para saber a qué periodo corresponden estos datos.
     *
     * @param mes El mes para los consumos (de 1 a 12).
     * @param anio El año para los consumos (ej. 2025).
     * @throws IllegalArgumentException si el mes o el año no son válidos.
     */
    public void mInicializarConsumos(int mes, int anio) {
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe ser un número entre 1 y 12. Ingresaste: " + mes);
        }
        // Se define un rango razonable para el año.
        if (anio < 1900 || anio > YearMonth.now().getYear() + 5) {
            throw new IllegalArgumentException("El año ingresado no parece válido. Ingresaste: " + anio);
        }

        YearMonth informacionMesAnio = YearMonth.of(anio, mes);
        int numeroDeDias = informacionMesAnio.lengthOfMonth(); // Da 28, 29, 30 o 31 días.
        this.consumosMensuales = new double[numeroDeDias][24]; // Crea la matriz.
        this.anioActualConsumos = anio;
        this.mesActualConsumos = mes;
        // En Java, una matriz de 'double' se llena automáticamente con 0.0.
    }

    /**
     * Obtiene el valor de consumo (en kWh) para un día y hora específicos del mes actual cargado.
     *
     * @param dia El día del mes (del 1 al número de días que tenga el mes).
     * @param hora La hora del día (de 0 a 23).
     * @return El consumo en kWh.
     * @throws IllegalStateException Si no se han cargado (inicializado) los consumos para ningún mes.
     * @throws IllegalArgumentException Si el día o la hora están fuera de los límites válidos.
     */
    public double mGetConsumoEn(int dia, int hora) {
        if (consumosMensuales == null) {
            throw new IllegalStateException("Aún no se han cargado los datos de consumo para este medidor.");
        }
        if (dia < 1 || dia > consumosMensuales.length || hora < 0 || hora > 23) {
            throw new IllegalArgumentException("El día (" + dia + ") o la hora (" + hora + ") no son válidos para el mes cargado (que tiene " + consumosMensuales.length + " días).");
        }
        return consumosMensuales[dia - 1][hora]; // Los arrays empiezan en 0, por eso día-1.
    }

    /**
     * Registra un valor de consumo (en kWh) para un día y hora específicos.
     * Es necesario que los consumos para el mes y año correspondientes ya hayan sido inicializados.
     *
     * @param dia El día del mes (del 1 al número de días que tenga el mes).
     * @param hora La hora del día (de 0 a 23).
     * @param valor El nuevo valor de consumo en kWh. No puede ser negativo.
     * @throws IllegalStateException Si no se han cargado (inicializado) los consumos.
     * @throws IllegalArgumentException Si el día, hora o valor son inválidos.
     */
    public void mSetConsumoEn(int dia, int hora, double valor) {
        if (consumosMensuales == null) {
            throw new IllegalStateException("Aún no se han cargado los datos de consumo para este medidor.");
        }
        if (dia < 1 || dia > consumosMensuales.length || hora < 0 || hora > 23) {
            throw new IllegalArgumentException("El día (" + dia + ") o la hora (" + hora + ") no son válidos para el mes cargado (que tiene " + consumosMensuales.length + " días).");
        }
        if (valor < 0) {
            throw new IllegalArgumentException("El valor de consumo no puede ser negativo. Ingresaste: " + valor);
        }
        consumosMensuales[dia - 1][hora] = valor;
    }

    // --- Métodos estándar de Java ---

    /**
     * Devuelve un texto que describe al medidor con su información principal.
     * @return Una cadena de texto con los datos del medidor.
     */
    @Override
    public String toString() {
        String mesConsumoStr = (mesActualConsumos > 0) ? String.valueOf(mesActualConsumos) : "No disponible";
        String anioConsumoStr = (anioActualConsumos > 0) ? String.valueOf(anioActualConsumos) : "No disponible";

        return "Registrador {" +
                "ID: '" + numeroIdentificacion + '\'' +
                ", Dirección: '" + direccion + '\'' +
                ", Ciudad: '" + ciudad + '\'' +
                ", Mes de Consumos Cargados: " + mesConsumoStr +
                ", Año de Consumos Cargados: " + anioConsumoStr +
                '}';
    }
}
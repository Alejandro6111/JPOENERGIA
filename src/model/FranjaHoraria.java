package model;

/**
 * Define una franja horaria para el consumo de energía.
 * Cada franja tiene un rango de horas, un rango de consumo en kWh,
 * y un precio específico por kWh que se aplica si el consumo cae dentro de esos rangos.
 * Esta clase ayuda a determinar el costo de la energía según las reglas del proyecto.
 */
public class FranjaHoraria {

    /** Hora en que inicia la franja (inclusive, formato 0-23). */
    private final int horaInicio;
    /** Hora en que termina la franja (inclusive, formato 0-23). */
    private final int horaFin;
    /** Consumo mínimo en kWh para que esta franja aplique (inclusive). */
    private final double kWhMin;
    /** Consumo máximo en kWh para que esta franja aplique (puede ser inclusive o exclusivo según la definición de la franja). */
    private final double kWhMax;
    /** Precio en pesos colombianos (COP) por cada kWh consumido dentro de esta franja. */
    private final double precioPorKw;

    /**
     * Constructor para crear una franja horaria. Es privado porque las franjas
     * se obtienen a través del método estático {@link #mObtenerFranja(int, double)}.
     *
     * @param horaInicio Hora de inicio (0-23).
     * @param horaFin Hora de fin (0-23).
     * @param kWhMin Consumo mínimo en kWh.
     * @param kWhMax Consumo máximo en kWh.
     * @param precioPorKw Precio por kWh para esta franja.
     */
    private FranjaHoraria(int horaInicio, int horaFin, double kWhMin, double kWhMax, double precioPorKw) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.kWhMin = kWhMin;
        this.kWhMax = kWhMax;
        this.precioPorKw = precioPorKw;
    }

    /**
     * Devuelve el precio por kilovatio-hora (kWh) establecido para esta franja.
     * @return El precio en COP por kWh.
     */
    public double mGetPrecioPorKw() {
        return precioPorKw;
    }

    /**
     * Determina qué franja horaria se debe aplicar a un consumo específico,
     * basándose en la hora del día y la cantidad de kWh consumidos.
     * Las franjas y sus condiciones son las siguientes, según el proyecto:
     *
     * - **Franja 1:**
     * - Horario: Entre las 00:00 y las 06:00 (inclusive).
     * - Consumo: Entre 100 kWh y 300 kWh (ambos inclusive).
     * - Tarifa: 200 COP por kWh.
     *
     * - **Franja 2:**
     * - Horario: Entre las 07:00 y las 17:00 (inclusive).
     * - Consumo: Mayor a 300 kWh y hasta 600 kWh (inclusive).
     * - Tarifa: 300 COP por kWh.
     *
     * - **Franja 3:**
     * - Horario: Entre las 18:00 y las 23:00 (inclusive).
     * - Consumo: Mayor a 600 kWh y menor estricto a 1000 kWh.
     * - Tarifa: 500 COP por kWh.
     *
     * @param hora La hora del día en que ocurrió el consumo (formato 0-23).
     * @param kWh La cantidad de energía consumida en kilovatios-hora.
     * @return Un objeto {@code FranjaHoraria} si el consumo cae dentro de alguna de las franjas definidas.
     * Devuelve {@code null} si el consumo no cumple las condiciones de kWh para la hora dada.
     */
    public static FranjaHoraria mObtenerFranja(int hora, double kWh) {
        // Evalúa Franja 1
        if (hora >= 0 && hora <= 6) { // Horario de Franja 1
            if (kWh >= 100 && kWh <= 300) { // Condición de kWh para Franja 1
                return new FranjaHoraria(0, 6, 100, 300, 200);
            }
        }
        // Evalúa Franja 2
        else if (hora >= 7 && hora <= 17) { // Horario de Franja 2
            if (kWh > 300 && kWh <= 600) { // Condición de kWh para Franja 2
                // Usamos 300.01 como kWhMin para ser explícitos con "mayor a 300"
                return new FranjaHoraria(7, 17, 300.01, 600, 300);
            }
        }
        // Evalúa Franja 3
        else if (hora >= 18 && hora <= 23) { // Horario de Franja 3
            if (kWh > 600 && kWh < 1000) { // Condición de kWh para Franja 3
                // Usamos 600.01 y 999.99 para ser explícitos con "mayor a 600" y "menor a 1000"
                return new FranjaHoraria(18, 23, 600.01, 999.99, 500);
            }
        }

        // Si ninguna franja aplicó (por ejemplo, kWh fuera de los rangos para la hora).
        return null;
    }

    /**
     * Devuelve un texto que describe esta FranjaHoraria.
     * @return Una cadena de texto con los detalles de la franja.
     */
    @Override
    public String toString() {
        return "FranjaHoraria {" +
                "De " + horaInicio + ":00 a " + horaFin + ":59" +
                ", Consumo kWh: entre " + kWhMin + " y " + kWhMax +
                ", Precio por kWh: " + precioPorKw + " COP" +
                '}';
    }
}
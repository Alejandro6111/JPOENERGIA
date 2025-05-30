package model;

/**
 * Representa una franja horaria de consumo energético que define un rango de horas,
 * un rango de consumo en kWh, y un precio por kilovatio hora (kWh).
 * Se utiliza para calcular el costo del consumo según la hora y el nivel de consumo.
 */
public class FranjaHoraria {


    private final int horaInicio;
    private final int horaFin;
    private final double kWhMin;
    private final double kWhMax;
    private final double precioPorKw;

    /**
     * Constructor que inicializa una franja horaria con sus parámetros.
     *
     * @param horaInicio Hora inicial de la franja (0 a 23).
     * @param horaFin Hora final de la franja (0 a 23).
     * @param kWhMin Consumo mínimo en kWh.
     * @param kWhMax Consumo máximo en kWh.
     * @param precioPorKw Precio por kilovatio hora en esta franja.
     */
    public FranjaHoraria(int horaInicio, int horaFin, double kWhMin, double kWhMax, double precioPorKw) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.kWhMin = kWhMin;
        this.kWhMax = kWhMax;
        this.precioPorKw = precioPorKw;
    }

    /**
     * Verifica si esta franja aplica a una hora y consumo determinados.
     *
     * @param hora Hora del consumo (0 a 23).
     * @param kWh Consumo en kilovatios hora.
     * @return true si la hora y el consumo se encuentran dentro de los rangos definidos, false en caso contrario.
     */
    public boolean aplica(int hora, double kWh) {
        return hora >= horaInicio && hora <= horaFin && kWh >= kWhMin && kWh <= kWhMax;
    }
    
    /**
     * Obtiene el precio por kilovatio hora de esta franja.
     * @return Precio en COP por kWh.
     */
    public double getPrecioPorKw() {
        return precioPorKw;
    }

  /**
     * Método estático que determina cuál franja horaria aplica según la hora y el consumo.
     * Se evalúan tres franjas definidas:
     * <ul>
     *   <li><b>Franja 1:</b> 00:00–06:00, 100–300 kWh → 200 COP/kWh</li>
     *   <li><b>Franja 2:</b> 07:00–17:00, 300.01–600 kWh → 300 COP/kWh</li>
     *   <li><b>Franja 3:</b> 18:00–23:00, 600.01–999.99 kWh → 500 COP/kWh</li>
     * </ul>
     *
     * @param hora Hora del consumo (0 a 23).
     * @param kWh Consumo en kilovatios hora.
     * @return La franja horaria aplicable, o null si ninguna aplica.
     */
    public static FranjaHoraria obtenerFranja(int hora, double kWh) {
        // Franja 1: 00:00 - 06:00, 100 <= kWh <= 300, 200 COP/kWh
        if (hora >= 0 && hora <= 6 && kWh >= 100 && kWh <= 300) {
            return new FranjaHoraria(0, 6, 100, 300, 200);
        }
        // Franja 2: 07:00 - 17:00, 300 < kWh <= 600, 300 COP/kWh
        else if (hora >= 7 && hora <= 17 && kWh > 300 && kWh <= 600) {
            return new FranjaHoraria(7, 17, 300.01, 600, 300);
        }
        // Franja 3: 18:00 - 23:00, 600 < kWh < 1000, 500 COP/kWh
        else if (hora >= 18 && hora <= 23 && kWh > 600 && kWh < 1000) {
            return new FranjaHoraria(18, 23, 600.01, 999.99, 500);
        }

        // Si no aplica ninguna franja
        return null;
    }
}

package model;

import java.time.LocalDateTime;

/**
 * Clase que representa un registro de consumo de energía eléctrica.
 * Contiene la fecha y hora del consumo, la cantidad de energía consumida en kWh,
 * y puede calcular su costo con base en una franja horaria definida.
 */
public class Consumo {

     /** Fecha y hora exacta del consumo */    
    private LocalDateTime fechaHora;

    /** Cantidad de energía consumida en kilovatios hora (kWh) */
    private double kWh;

    /**
     * Constructor que inicializa un consumo con fecha y cantidad de kWh.
     * @param fechaHora Fecha y hora del consumo.
     * @param kWh Cantidad de energía consumida en kWh.
     */
    public Consumo(LocalDateTime fechaHora, double kWh) {
        this.fechaHora = fechaHora;
        this.kWh = kWh;
    }

    
    /**
     * Obtiene la fecha y hora del consumo.
     * @return Fecha y hora del consumo.
     */
    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    /**
     * Establece una nueva fecha y hora para el consumo.
     * @param fechaHora Nueva fecha y hora del consumo.
     */
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * Obtiene la cantidad de energía consumida en kWh.
     * @return Energía consumida en kilovatios hora.
     */
    public double getKWh() {
        return kWh;
    }

     /**
     * Establece una nueva cantidad de energía consumida.
     * @param kWh Nueva cantidad de energía en kilovatios hora.
     */
    public void setKWh(double kWh) {
        this.kWh = kWh;
    }

     /**
     * Representación en texto del objeto Consumo.
     * @return Cadena con los datos del consumo.
     */
    @Override
    public String toString() {
        return "Consumo{" +
                "fechaHora=" + fechaHora +
                ", kWh=" + kWh +
                '}';
    }

    /**
     * Calcula el costo del consumo eléctrico basado en la franja horaria y cantidad de kWh.
     * <ul>
     *   <li>Si el consumo es menor a 100 kWh, se aplica una tarifa plana de 100 COP por kWh.</li>
     *   <li>Si el consumo supera ese límite, se determina la franja horaria según la hora del consumo
     *       y se usa su tarifa específica.</li>
     *   <li>Si no se encuentra una franja válida, el costo es 0.</li>
     * </ul>
     *
     * @return El costo total del consumo en pesos (COP).
     */
    public double calcularCosto() {
        if (kWh < 100) {
            return kWh * 100; // Tarifa especial para consumo bajo
        }

        int hora = fechaHora.getHour();
        FranjaHoraria franja = FranjaHoraria.obtenerFranja(hora, kWh);

        if (franja != null) {
            return kWh * franja.getPrecioPorKw();
        }

        return 0; // Si no entra en ninguna franja válida
    }
}

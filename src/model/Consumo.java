package model;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Representa un registro individual de consumo de energía eléctrica en un momento dado.
 * Guarda la fecha y hora exactas del consumo y la cantidad de energía en kilovatios-hora (kWh).
 * También puede calcular el costo de este consumo específico basándose en las tarifas
 * por franja horaria definidas en el proyecto.
 */
public class Consumo {

    /** La fecha y hora exactas en que se registró el consumo. */
    private LocalDateTime fechaHora;

    /** La cantidad de energía consumida, medida en kilovatios-hora (kWh). */
    private double kWh;

    /**
     * Crea un nuevo registro de consumo.
     *
     * @param fechaHora El momento exacto del consumo. No puede ser nulo.
     * @param kWh La cantidad de energía consumida. No puede ser un valor negativo.
     * @throws IllegalArgumentException Si la fechaHora es nula o si los kWh son negativos.
     */
    public Consumo(LocalDateTime fechaHora, double kWh) {
        if (fechaHora == null) {
            throw new IllegalArgumentException("La fecha y hora del consumo son obligatorias.");
        }
        if (kWh < 0) {
            throw new IllegalArgumentException("La cantidad de kWh consumidos no puede ser negativa. Valor recibido: " + kWh);
        }
        this.fechaHora = fechaHora;
        this.kWh = kWh;
    }

    /**
     * Obtiene la fecha y hora de este consumo.
     * @return Un objeto LocalDateTime con la fecha y hora.
     */
    public LocalDateTime mGetFechaHora() {
        return fechaHora;
    }

    /**
     * Permite cambiar la fecha y hora de este consumo.
     * @param fechaHora La nueva fecha y hora. No puede ser nula.
     * @throws IllegalArgumentException Si la fechaHora es nula.
     */
    public void mSetFechaHora(LocalDateTime fechaHora) {
        if (fechaHora == null) {
            throw new IllegalArgumentException("La fecha y hora del consumo son obligatorias.");
        }
        this.fechaHora = fechaHora;
    }

    /**
     * Obtiene la cantidad de energía consumida en kWh.
     * @return La cantidad de kWh.
     */
    public double mGetKWh() {
        return kWh;
    }

    /**
     * Permite cambiar la cantidad de energía consumida.
     * @param kWh La nueva cantidad de kWh. No puede ser un valor negativo.
     * @throws IllegalArgumentException si los kWh son negativos.
     */
    public void mSetKWh(double kWh) {
        if (kWh < 0) {
            throw new IllegalArgumentException("La cantidad de kWh consumidos no puede ser negativa. Valor recibido: " + kWh);
        }
        this.kWh = kWh;
    }

    /**
     * Calcula el costo de este consumo de energía, aplicando las tarifas según la hora y la cantidad de kWh.
     * Las reglas de tarificación son:
     * - Franja 1: De 00:00 a 06:00, si el consumo está entre 100 kWh y 300 kWh (ambos inclusive),
     * el precio es de 200 pesos colombianos (COP) por cada kWh.
     * - Franja 2: De 07:00 a 17:00, si el consumo es mayor a 300 kWh y hasta 600 kWh (inclusive),
     * el precio es de 300 COP por cada kWh.
     * - Franja 3: De 18:00 a 23:00, si el consumo es mayor a 600 kWh y menor estricto a 1000 kWh,
     * el precio es de 500 COP por cada kWh.
     *
     * Si el consumo no encaja en ninguna de estas franjas (por ejemplo, si los kWh están fuera
     * de los rangos establecidos para la hora en que ocurrió), el costo se considera 0.
     * No hay tarifas especiales para consumos bajos fuera de estas franjas.
     *
     * @return El costo total del consumo en pesos colombianos (COP).
     */
    public double mCalcularCosto() {
        int horaDelConsumo = fechaHora.getHour();
        FranjaHoraria franjaAplicable = FranjaHoraria.mObtenerFranja(horaDelConsumo, this.kWh);

        if (franjaAplicable != null) {
            // Si se encontró una franja, se calcula el costo.
            return this.kWh * franjaAplicable.mGetPrecioPorKw();
        }

        // Si no aplica ninguna franja tarifaria (ej. kWh muy bajos o muy altos para la hora,
        // o una hora sin franjas definidas), el costo es cero.
        return 0;
    }

    /**
     * Devuelve un texto que representa este objeto Consumo, incluyendo su costo calculado.
     * @return Una cadena de texto con los detalles del consumo.
     */
    @Override
    public String toString() {
        return "Consumo {" +
                "Fecha y Hora = " + fechaHora +
                ", kWh = " + kWh +
                ", Costo Calculado = " + String.format("%.2f", mCalcularCosto()) + " COP" +
                '}';
    }

    /**
     * Compara este Consumo con otro objeto para ver si son iguales.
     * Dos consumos se consideran iguales si ocurrieron en la misma fecha y hora, y tienen la misma cantidad de kWh.
     * @param o El objeto con el que se va a comparar.
     * @return true si son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Consumo consumo = (Consumo) o;
        return Double.compare(consumo.kWh, kWh) == 0 && // Compara los doubles de forma segura
               Objects.equals(fechaHora, consumo.fechaHora);
    }

    /**
     * Genera un código hash para este Consumo, basado en su fechaHora y kWh.
     * @return El código hash.
     */
    @Override
    public int hashCode() {
        return Objects.hash(fechaHora, kWh);
    }
}
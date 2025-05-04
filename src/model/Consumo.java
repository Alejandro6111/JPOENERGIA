package model; // Pertece al paquete model

import java.time.LocalDateTime; // Para manejar fecha y hora

public class Consumo {
    // Atributos del consumo
    private LocalDateTime fechaHora; // Momento exacto en que se registra el consumo
    private double KWH; // Cantidad de Killowatts/Hora consumidos en esa hora

    // Constructor
    public Consumo(LocalDateTime fechaHora, double KWH) {
        this.fechaHora = fechaHora;
        this.KWH = KWH;
    }

    // Metodos Getters y Setters

    // Getter para la fecha y hora del consumo
    public LocalDateTime mGetFechaHora() {
        return fechaHora;
    }

    // Setter para la fecha y hora del consumo
    public void mSetFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    // Getter para el valor en Killowatts/Hora
    public double mGetKWH() {
        return KWH;
    }

    // Setter para el valor en Killowatts/Hora
    public void mSetKWH(double KWH) {
        this.KWH = KWH;
    }

    @Override // sobreescribimos el metodo toString
    public String toString() {
        return "Consumo{" +
                "fechaHora=" + fechaHora +
                ", KWH=" + KWH +
                '}';
    }

    // Metodo para calcular el costo del consumo
    public double mCalcularCosto(double tarifa , int año, int mes, int dia, int hora, int minuto){
        // Obtenemos la fecha y hora del consumo
        // Removed unused local variable fechaHora
        // Obtenemos el valor de KWH
        double KWH = this.KWH;

        // Comprobamos si el consumo es menor a 100
        if (KWH < 100) {
            return KWH * 100; // Tarifa de 100 por KWH
        }
    // Comprobamos si el consumo es mayor a 100 y menor a 300
    if (hora <= 6 && KWH >= 100 && KWH <= 300) {
        return KWH * 200; // Tarifa de 200 por KWH
    } else if (hora >= 7 && hora <= 17 && KWH > 300 && KWH <= 600) {
        return KWH * 300; // Tarifa de 300 por KWH
    } else if (hora >= 18 && hora <= 23 && KWH > 600 && KWH <= 1000) {
        return KWH * 500; // Tarifa de 500 por KWH
    } else {
        return 0; // No se aplica tarifa
    }

}
}

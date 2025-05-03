package model; // Pertece al paquete model

import java.time.LocalDateTime; // Para manejar fecha y hora


public class Consumo {
    // Atributos del consumo 
    private LocalDateTime fechaHora; // Momento exacto en que se registra el consumo
    private double valorKWH; // Cantidad de Killowatts/Hora consumidos en esa hora

    // Constructor 
    public Consumo(LocalDateTime fechaHora, double valorKWH){
        this.fechaHora = fechaHora;
        this.valorKWH = valorKWH;
    }

    // Metodos Getters y Setters 

    // Getter para la fecha y hora del consumo
    public LocalDateTime mGetFechaHora(){
        return fechaHora;
    }

    // Setter para la fecha y hora del consumo
    public void mSetFechaHora(LocalDateTime fechaHora){
        this.fechaHora = fechaHora;
    }

    // Getter para el valor en Killowatts/Hora
    public double mGetValorKWH(){
        return valorKWH;
    }

    // Setter para el valor en Killowatts/Hora
    public void mSetValorKWH(double valorKWH){
        this.valorKWH = valorKWH;
    }

    @Override // sobreescribimos el metodo toString
    public String toString(){
        return "Consumo{" + 
                "fechaHora=" + fechaHora + 
                ", valorKWH=" + valorKWH + 
                '}';
    }








}

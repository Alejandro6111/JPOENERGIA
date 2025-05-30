package model;

import java.util.*;
import java.time.YearMonth;
import java.time.LocalDateTime;

/**
 * Representa un registrador de consumo eléctrico que almacena consumos horarios asociados a meses específicos.
 * Cada registrador tiene un identificador único, dirección y ciudad.
 */
public class Registrador {

    // Atributos del registrador
    private final String numeroIdentificacion;
    private String direccion;
    private String ciudad;

    /**
     * Mapa que asocia cada mes (YearMonth) con su lista de consumos horarios (List<Consumo>).
     * Permite organizar los consumos por períodos mensuales.
     */
    private Map<YearMonth, List<Consumo>> consumosPorMes;

    /**
     * Constructor que inicializa un registrador con su número de identificación, dirección y ciudad.
     *
     * @param numeroIdentificacion Identificador único del registrador.
     * @param direccion Dirección física del registrador.
     * @param ciudad Ciudad donde se encuentra el registrador.
     */
    public Registrador(String numeroIdentificacion, String direccion, String ciudad) {
        this.numeroIdentificacion = numeroIdentificacion;
        this.direccion = direccion;
        this.ciudad = ciudad;
        this.consumosPorMes = new HashMap<>();
    }

    // Getters
    public String getNumeroIdentificacion() {
        return numeroIdentificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    // Setters
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    // Obtener todos los consumos
    public Map<YearMonth, List<Consumo>> getConsumosPorMes() {
        return consumosPorMes;
    }

    // Obtener consumos de un mes específico
    public List<Consumo> getConsumosDelMes(YearMonth mes) {
        return consumosPorMes.getOrDefault(mes, new ArrayList<>());
    }

   
    /**
     * Agrega un nuevo consumo a la lista de consumos de un mes específico.
     * Si no existe la lista para ese mes, se crea automáticamente.
     *
     * @param mes Mes al que se asocia el consumo.
     * @param consumo Objeto Consumo que contiene la fecha/hora y kWh consumidos.
     */
    public void agregarConsumo(YearMonth mes, Consumo consumo) {
        consumosPorMes.computeIfAbsent(mes, k -> new ArrayList<>()).add(consumo);
    }

    /**
     * Modifica el consumo horario en una fecha y hora específica.
     * Busca el consumo exacto y si lo encuentra actualiza su valor de kWh.
     *
     * @param fechaHora Fecha y hora del consumo a modificar.
     * @param nuevoKwH Nuevo valor en kWh que se asignará.
     * @return true si se encontró y modificó el consumo, false si no se encontró.
     */
    public boolean modificarConsumo(LocalDateTime fechaHora, double nuevoKwH) {
        YearMonth mes = YearMonth.from(fechaHora);
        List<Consumo> consumos = consumosPorMes.get(mes);
        if (consumos != null) {
            for (Consumo c : consumos) {
                if (c.getFechaHora().equals(fechaHora)) {
                    c.setKWh(nuevoKwH);
                    return true;
                }
            }
        }
        return false;
    }

     /**
     * Carga consumos horarios para un mes completo con valores aleatorios simulados entre 0 y 100 kWh.
     * Genera un consumo para cada hora de cada día del mes.
     *
     * @param ym Mes (YearMonth) para el que se cargarán los consumos.
     */
    public void cargarConsumoMensual(YearMonth ym) {
        for (int dia = 1; dia <= ym.lengthOfMonth(); dia++) {
            for (int hora = 0; hora < 24; hora++) {
                LocalDateTime fechaHora = LocalDateTime.of(ym.getYear(), ym.getMonthValue(), dia, hora, 0);
                double consumo = Math.random() * 100; // Simula un consumo aleatorio entre 0 y 100 kWh
                agregarConsumo(ym, new Consumo(fechaHora, consumo)); // Añade el consumo al mes
            }
    }
}
}
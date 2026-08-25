/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.SIGCAP.modelo;

import java.time.LocalDate;

/**
 *
 * @author Fabufabri
 */
public class OrdenTrabajo {

    private int ot_id;
    private String ot_codigo;
    private String ot_cliente;
    private String ot_campania;
    private String ot_solicitante;
    private String ot_ciudad;
    private LocalDate ot_fecha;
    private LocalDate ot_fecha_requerida;
    private String ot_prioridad;
    private String ot_estado;
    private String ot_observaciones;
    private String ot_responsable;

    public OrdenTrabajo() {
    }

    public OrdenTrabajo(int ot_id, String ot_codigo, String ot_cliente,
                        String ot_campania, String ot_solicitante,
                        String ot_ciudad, LocalDate ot_fecha,
                        LocalDate ot_fecha_requerida, String ot_prioridad,
                        String ot_estado, String ot_observaciones,
                        String ot_responsable) {
        this.ot_id = ot_id;
        this.ot_codigo = ot_codigo;
        this.ot_cliente = ot_cliente;
        this.ot_campania = ot_campania;
        this.ot_solicitante = ot_solicitante;
        this.ot_ciudad = ot_ciudad;
        this.ot_fecha = ot_fecha;
        this.ot_fecha_requerida = ot_fecha_requerida;
        this.ot_prioridad = ot_prioridad;
        this.ot_estado = ot_estado;
        this.ot_observaciones = ot_observaciones;
        this.ot_responsable = ot_responsable;
    }

    public int getOt_id() {
        return ot_id;
    }

    public void setOt_id(int ot_id) {
        this.ot_id = ot_id;
    }

    public String getOt_codigo() {
        return ot_codigo;
    }

    public void setOt_codigo(String ot_codigo) {
        this.ot_codigo = ot_codigo;
    }

    public String getOt_cliente() {
        return ot_cliente;
    }

    public void setOt_cliente(String ot_cliente) {
        this.ot_cliente = ot_cliente;
    }

    public String getOt_campania() {
        return ot_campania;
    }

    public void setOt_campania(String ot_campania) {
        this.ot_campania = ot_campania;
    }

    public String getOt_solicitante() {
        return ot_solicitante;
    }

    public void setOt_solicitante(String ot_solicitante) {
        this.ot_solicitante = ot_solicitante;
    }

    public String getOt_ciudad() {
        return ot_ciudad;
    }

    public void setOt_ciudad(String ot_ciudad) {
        this.ot_ciudad = ot_ciudad;
    }

    public LocalDate getOt_fecha() {
        return ot_fecha;
    }

    public void setOt_fecha(LocalDate ot_fecha) {
        this.ot_fecha = ot_fecha;
    }

    public LocalDate getOt_fecha_requerida() {
        return ot_fecha_requerida;
    }

    public void setOt_fecha_requerida(LocalDate ot_fecha_requerida) {
        this.ot_fecha_requerida = ot_fecha_requerida;
    }

    public String getOt_prioridad() {
        return ot_prioridad;
    }

    public void setOt_prioridad(String ot_prioridad) {
        this.ot_prioridad = ot_prioridad;
    }

    public String getOt_estado() {
        return ot_estado;
    }

    public void setOt_estado(String ot_estado) {
        this.ot_estado = ot_estado;
    }

    public String getOt_observaciones() {
        return ot_observaciones;
    }

    public void setOt_observaciones(String ot_observaciones) {
        this.ot_observaciones = ot_observaciones;
    }

    public String getOt_responsable() {
        return ot_responsable;
    }

    public void setOt_responsable(String ot_responsable) {
        this.ot_responsable = ot_responsable;
    }
}//fin clase

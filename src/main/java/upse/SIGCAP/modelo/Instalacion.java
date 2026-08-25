// src/main/java/upse/SIGCAP/modelo/Instalacion.java

package upse.SIGCAP.modelo;

import java.time.LocalDate;

public class Instalacion {

    private int ins_id;
    private int itm_id;
    private int ter_id;
    private int tin_id;
    private LocalDate ins_fecha_programada;
    private LocalDate ins_fecha_real;
    private String ins_estado;
    private String ins_observaciones;
    private String ins_evidencia;

    public Instalacion() {
    }

    public Instalacion(
            int ins_id,
            int itm_id,
            int ter_id,
            int tin_id,
            LocalDate ins_fecha_programada,
            LocalDate ins_fecha_real,
            String ins_estado,
            String ins_observaciones,
            String ins_evidencia) {

        this.ins_id = ins_id;
        this.itm_id = itm_id;
        this.ter_id = ter_id;
        this.tin_id = tin_id;
        this.ins_fecha_programada = ins_fecha_programada;
        this.ins_fecha_real = ins_fecha_real;
        this.ins_estado = ins_estado;
        this.ins_observaciones = ins_observaciones;
        this.ins_evidencia = ins_evidencia;
    }

    public int getIns_id() {
        return ins_id;
    }

    public void setIns_id(int ins_id) {
        this.ins_id = ins_id;
    }

    public int getItm_id() {
        return itm_id;
    }

    public void setItm_id(int itm_id) {
        this.itm_id = itm_id;
    }

    public int getTer_id() {
        return ter_id;
    }

    public void setTer_id(int ter_id) {
        this.ter_id = ter_id;
    }

    public int getTin_id() {
        return tin_id;
    }

    public void setTin_id(int tin_id) {
        this.tin_id = tin_id;
    }

    public LocalDate getIns_fecha_programada() {
        return ins_fecha_programada;
    }

    public void setIns_fecha_programada(
            LocalDate ins_fecha_programada) {

        this.ins_fecha_programada =
                ins_fecha_programada;
    }

    public LocalDate getIns_fecha_real() {
        return ins_fecha_real;
    }

    public void setIns_fecha_real(
            LocalDate ins_fecha_real) {

        this.ins_fecha_real =
                ins_fecha_real;
    }

    public String getIns_estado() {
        return ins_estado;
    }

    public void setIns_estado(String ins_estado) {
        this.ins_estado = ins_estado;
    }

    public String getIns_observaciones() {
        return ins_observaciones;
    }

    public void setIns_observaciones(
            String ins_observaciones) {

        this.ins_observaciones =
                ins_observaciones;
    }

    public String getIns_evidencia() {
        return ins_evidencia;
    }

    public void setIns_evidencia(
            String ins_evidencia) {

        this.ins_evidencia =
                ins_evidencia;
    }

}//fin clase
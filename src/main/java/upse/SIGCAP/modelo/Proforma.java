// src/main/java/upse/SIGCAP/modelo/Proforma.java

package upse.SIGCAP.modelo;

import java.time.LocalDate;

public class Proforma {

    private int pfr_id;
    private int cli_id;
    private Integer cam_id;
    private String pfr_codigo;
    private LocalDate pfr_fecha;
    private LocalDate pfr_validez;
    private double pfr_subtotal;
    private double pfr_iva;
    private double pfr_total;
    private String pfr_estado;
    private String pfr_observaciones;

    private String cli_nombre;
    private String cam_nombre;

    public Proforma() {
    }

    public Proforma(
            int pfr_id,
            int cli_id,
            Integer cam_id,
            String pfr_codigo,
            LocalDate pfr_fecha,
            LocalDate pfr_validez,
            double pfr_subtotal,
            double pfr_iva,
            double pfr_total,
            String pfr_estado,
            String pfr_observaciones) {

        this.pfr_id = pfr_id;
        this.cli_id = cli_id;
        this.cam_id = cam_id;
        this.pfr_codigo = pfr_codigo;
        this.pfr_fecha = pfr_fecha;
        this.pfr_validez = pfr_validez;
        this.pfr_subtotal = pfr_subtotal;
        this.pfr_iva = pfr_iva;
        this.pfr_total = pfr_total;
        this.pfr_estado = pfr_estado;
        this.pfr_observaciones = pfr_observaciones;
    }

    public int getPfr_id() {
        return pfr_id;
    }

    public void setPfr_id(int pfr_id) {
        this.pfr_id = pfr_id;
    }

    public int getCli_id() {
        return cli_id;
    }

    public void setCli_id(int cli_id) {
        this.cli_id = cli_id;
    }

    public Integer getCam_id() {
        return cam_id;
    }

    public void setCam_id(Integer cam_id) {
        this.cam_id = cam_id;
    }

    public String getPfr_codigo() {
        return pfr_codigo;
    }

    public void setPfr_codigo(String pfr_codigo) {
        this.pfr_codigo = pfr_codigo;
    }

    public LocalDate getPfr_fecha() {
        return pfr_fecha;
    }

    public void setPfr_fecha(LocalDate pfr_fecha) {
        this.pfr_fecha = pfr_fecha;
    }

    public LocalDate getPfr_validez() {
        return pfr_validez;
    }

    public void setPfr_validez(LocalDate pfr_validez) {
        this.pfr_validez = pfr_validez;
    }

    public double getPfr_subtotal() {
        return pfr_subtotal;
    }

    public void setPfr_subtotal(double pfr_subtotal) {
        this.pfr_subtotal = pfr_subtotal;
    }

    public double getPfr_iva() {
        return pfr_iva;
    }

    public void setPfr_iva(double pfr_iva) {
        this.pfr_iva = pfr_iva;
    }

    public double getPfr_total() {
        return pfr_total;
    }

    public void setPfr_total(double pfr_total) {
        this.pfr_total = pfr_total;
    }

    public String getPfr_estado() {
        return pfr_estado;
    }

    public void setPfr_estado(String pfr_estado) {
        this.pfr_estado = pfr_estado;
    }

    public String getPfr_observaciones() {
        return pfr_observaciones;
    }

    public void setPfr_observaciones(String pfr_observaciones) {
        this.pfr_observaciones = pfr_observaciones;
    }

    public String getCli_nombre() {
        return cli_nombre;
    }

    public void setCli_nombre(String cli_nombre) {
        this.cli_nombre = cli_nombre;
    }

    public String getCam_nombre() {
        return cam_nombre;
    }

    public void setCam_nombre(String cam_nombre) {
        this.cam_nombre = cam_nombre;
    }

}//fin clase
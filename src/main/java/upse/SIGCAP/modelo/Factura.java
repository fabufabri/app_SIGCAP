// src/main/java/upse/SIGCAP/modelo/Factura.java

package upse.SIGCAP.modelo;

import java.time.LocalDate;

public class Factura {

    private int fac_id;
    private int cli_id;
    private Integer pfr_id;
    private Integer ot_id;
    private String fac_numero;
    private LocalDate fac_fecha;
    private double fac_subtotal;
    private double fac_iva;
    private double fac_total;
    private String fac_estado;
    private String fac_observaciones;

    private String cli_nombre;
    private String pfr_codigo;
    private String ot_codigo;

    public Factura() {
    }

    public Factura(
            int fac_id,
            int cli_id,
            Integer pfr_id,
            Integer ot_id,
            String fac_numero,
            LocalDate fac_fecha,
            double fac_subtotal,
            double fac_iva,
            double fac_total,
            String fac_estado,
            String fac_observaciones) {

        this.fac_id = fac_id;
        this.cli_id = cli_id;
        this.pfr_id = pfr_id;
        this.ot_id = ot_id;
        this.fac_numero = fac_numero;
        this.fac_fecha = fac_fecha;
        this.fac_subtotal = fac_subtotal;
        this.fac_iva = fac_iva;
        this.fac_total = fac_total;
        this.fac_estado = fac_estado;
        this.fac_observaciones = fac_observaciones;
    }

    public int getFac_id() {
        return fac_id;
    }

    public void setFac_id(int fac_id) {
        this.fac_id = fac_id;
    }

    public int getCli_id() {
        return cli_id;
    }

    public void setCli_id(int cli_id) {
        this.cli_id = cli_id;
    }

    public Integer getPfr_id() {
        return pfr_id;
    }

    public void setPfr_id(Integer pfr_id) {
        this.pfr_id = pfr_id;
    }

    public Integer getOt_id() {
        return ot_id;
    }

    public void setOt_id(Integer ot_id) {
        this.ot_id = ot_id;
    }

    public String getFac_numero() {
        return fac_numero;
    }

    public void setFac_numero(String fac_numero) {
        this.fac_numero = fac_numero;
    }

    public LocalDate getFac_fecha() {
        return fac_fecha;
    }

    public void setFac_fecha(LocalDate fac_fecha) {
        this.fac_fecha = fac_fecha;
    }

    public double getFac_subtotal() {
        return fac_subtotal;
    }

    public void setFac_subtotal(double fac_subtotal) {
        this.fac_subtotal = fac_subtotal;
    }

    public double getFac_iva() {
        return fac_iva;
    }

    public void setFac_iva(double fac_iva) {
        this.fac_iva = fac_iva;
    }

    public double getFac_total() {
        return fac_total;
    }

    public void setFac_total(double fac_total) {
        this.fac_total = fac_total;
    }

    public String getFac_estado() {
        return fac_estado;
    }

    public void setFac_estado(String fac_estado) {
        this.fac_estado = fac_estado;
    }

    public String getFac_observaciones() {
        return fac_observaciones;
    }

    public void setFac_observaciones(String fac_observaciones) {
        this.fac_observaciones = fac_observaciones;
    }

    public String getCli_nombre() {
        return cli_nombre;
    }

    public void setCli_nombre(String cli_nombre) {
        this.cli_nombre = cli_nombre;
    }

    public String getPfr_codigo() {
        return pfr_codigo;
    }

    public void setPfr_codigo(String pfr_codigo) {
        this.pfr_codigo = pfr_codigo;
    }

    public String getOt_codigo() {
        return ot_codigo;
    }

    public void setOt_codigo(String ot_codigo) {
        this.ot_codigo = ot_codigo;
    }

}//fin clase
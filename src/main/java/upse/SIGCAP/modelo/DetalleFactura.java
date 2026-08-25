// src/main/java/upse/SIGCAP/modelo/DetalleFactura.java

package upse.SIGCAP.modelo;

public class DetalleFactura {

    private int dfa_id;
    private int fac_id;
    private String dfa_descripcion;
    private int dfa_cantidad;
    private double dfa_precio_unitario;
    private double dfa_descuento;
    private double dfa_total;

    public DetalleFactura() {
    }

    public DetalleFactura(
            int dfa_id,
            int fac_id,
            String dfa_descripcion,
            int dfa_cantidad,
            double dfa_precio_unitario,
            double dfa_descuento,
            double dfa_total) {

        this.dfa_id = dfa_id;
        this.fac_id = fac_id;
        this.dfa_descripcion = dfa_descripcion;
        this.dfa_cantidad = dfa_cantidad;
        this.dfa_precio_unitario = dfa_precio_unitario;
        this.dfa_descuento = dfa_descuento;
        this.dfa_total = dfa_total;
    }

    public int getDfa_id() {
        return dfa_id;
    }

    public void setDfa_id(int dfa_id) {
        this.dfa_id = dfa_id;
    }

    public int getFac_id() {
        return fac_id;
    }

    public void setFac_id(int fac_id) {
        this.fac_id = fac_id;
    }

    public String getDfa_descripcion() {
        return dfa_descripcion;
    }

    public void setDfa_descripcion(String dfa_descripcion) {
        this.dfa_descripcion = dfa_descripcion;
    }

    public int getDfa_cantidad() {
        return dfa_cantidad;
    }

    public void setDfa_cantidad(int dfa_cantidad) {
        this.dfa_cantidad = dfa_cantidad;
    }

    public double getDfa_precio_unitario() {
        return dfa_precio_unitario;
    }

    public void setDfa_precio_unitario(double dfa_precio_unitario) {
        this.dfa_precio_unitario = dfa_precio_unitario;
    }

    public double getDfa_descuento() {
        return dfa_descuento;
    }

    public void setDfa_descuento(double dfa_descuento) {
        this.dfa_descuento = dfa_descuento;
    }

    public double getDfa_total() {
        return dfa_total;
    }

    public void setDfa_total(double dfa_total) {
        this.dfa_total = dfa_total;
    }

}//fin clase
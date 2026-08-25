// src/main/java/upse/SIGCAP/modelo/DetalleProforma.java

package upse.SIGCAP.modelo;

public class DetalleProforma {

    private int dpf_id;
    private int pfr_id;
    private Integer pro_id;
    private Integer med_id;
    private Integer mat_id;
    private String dpf_descripcion;
    private int dpf_cantidad;
    private double dpf_precio_unitario;
    private double dpf_descuento;
    private double dpf_total;

    private String pro_nombre;
    private String med_nombre;
    private String mat_nombre;

    public DetalleProforma() {
    }

    public DetalleProforma(
            int dpf_id,
            int pfr_id,
            Integer pro_id,
            Integer med_id,
            Integer mat_id,
            String dpf_descripcion,
            int dpf_cantidad,
            double dpf_precio_unitario,
            double dpf_descuento,
            double dpf_total) {

        this.dpf_id = dpf_id;
        this.pfr_id = pfr_id;
        this.pro_id = pro_id;
        this.med_id = med_id;
        this.mat_id = mat_id;
        this.dpf_descripcion = dpf_descripcion;
        this.dpf_cantidad = dpf_cantidad;
        this.dpf_precio_unitario = dpf_precio_unitario;
        this.dpf_descuento = dpf_descuento;
        this.dpf_total = dpf_total;
    }

    public int getDpf_id() {
        return dpf_id;
    }

    public void setDpf_id(int dpf_id) {
        this.dpf_id = dpf_id;
    }

    public int getPfr_id() {
        return pfr_id;
    }

    public void setPfr_id(int pfr_id) {
        this.pfr_id = pfr_id;
    }

    public Integer getPro_id() {
        return pro_id;
    }

    public void setPro_id(Integer pro_id) {
        this.pro_id = pro_id;
    }

    public Integer getMed_id() {
        return med_id;
    }

    public void setMed_id(Integer med_id) {
        this.med_id = med_id;
    }

    public Integer getMat_id() {
        return mat_id;
    }

    public void setMat_id(Integer mat_id) {
        this.mat_id = mat_id;
    }

    public String getDpf_descripcion() {
        return dpf_descripcion;
    }

    public void setDpf_descripcion(String dpf_descripcion) {
        this.dpf_descripcion = dpf_descripcion;
    }

    public int getDpf_cantidad() {
        return dpf_cantidad;
    }

    public void setDpf_cantidad(int dpf_cantidad) {
        this.dpf_cantidad = dpf_cantidad;
    }

    public double getDpf_precio_unitario() {
        return dpf_precio_unitario;
    }

    public void setDpf_precio_unitario(double dpf_precio_unitario) {
        this.dpf_precio_unitario = dpf_precio_unitario;
    }

    public double getDpf_descuento() {
        return dpf_descuento;
    }

    public void setDpf_descuento(double dpf_descuento) {
        this.dpf_descuento = dpf_descuento;
    }

    public double getDpf_total() {
        return dpf_total;
    }

    public void setDpf_total(double dpf_total) {
        this.dpf_total = dpf_total;
    }

    public String getPro_nombre() {
        return pro_nombre;
    }

    public void setPro_nombre(String pro_nombre) {
        this.pro_nombre = pro_nombre;
    }

    public String getMed_nombre() {
        return med_nombre;
    }

    public void setMed_nombre(String med_nombre) {
        this.med_nombre = med_nombre;
    }

    public String getMat_nombre() {
        return mat_nombre;
    }

    public void setMat_nombre(String mat_nombre) {
        this.mat_nombre = mat_nombre;
    }

}//fin clase
// src/main/java/upse/SIGCAP/modelo/ItemOrdenTrabajo.java

package upse.SIGCAP.modelo;

public class ItemOrdenTrabajo {

    private int itm_id;
    private int ot_id;
    private String itm_ot_codigo;
    private String itm_local;
    private String itm_producto;
    private String itm_descripcion;
    private String itm_medida;
    private String itm_material;
    private int itm_cantidad;
    private String itm_instalacion;
    private String itm_estado;
    private int itm_progreso;
    private String itm_observaciones;

    public ItemOrdenTrabajo() {
    }

    public ItemOrdenTrabajo(
            int itm_id,
            int ot_id,
            String itm_ot_codigo,
            String itm_local,
            String itm_producto,
            String itm_descripcion,
            String itm_medida,
            String itm_material,
            int itm_cantidad,
            String itm_instalacion,
            String itm_estado,
            int itm_progreso,
            String itm_observaciones) {

        this.itm_id = itm_id;
        this.ot_id = ot_id;
        this.itm_ot_codigo = itm_ot_codigo;
        this.itm_local = itm_local;
        this.itm_producto = itm_producto;
        this.itm_descripcion = itm_descripcion;
        this.itm_medida = itm_medida;
        this.itm_material = itm_material;
        this.itm_cantidad = itm_cantidad;
        this.itm_instalacion = itm_instalacion;
        this.itm_estado = itm_estado;
        this.itm_progreso = itm_progreso;
        this.itm_observaciones = itm_observaciones;
    }

    public int getItm_id() {
        return itm_id;
    }

    public void setItm_id(int itm_id) {
        this.itm_id = itm_id;
    }

    public int getOt_id() {
        return ot_id;
    }

    public void setOt_id(int ot_id) {
        this.ot_id = ot_id;
    }

    public String getItm_ot_codigo() {
        return itm_ot_codigo;
    }

    public void setItm_ot_codigo(String itm_ot_codigo) {
        this.itm_ot_codigo = itm_ot_codigo;
    }

    public String getItm_local() {
        return itm_local;
    }

    public void setItm_local(String itm_local) {
        this.itm_local = itm_local;
    }

    public String getItm_producto() {
        return itm_producto;
    }

    public void setItm_producto(String itm_producto) {
        this.itm_producto = itm_producto;
    }

    public String getItm_descripcion() {
        return itm_descripcion;
    }

    public void setItm_descripcion(String itm_descripcion) {
        this.itm_descripcion = itm_descripcion;
    }

    public String getItm_medida() {
        return itm_medida;
    }

    public void setItm_medida(String itm_medida) {
        this.itm_medida = itm_medida;
    }

    public String getItm_material() {
        return itm_material;
    }

    public void setItm_material(String itm_material) {
        this.itm_material = itm_material;
    }

    public int getItm_cantidad() {
        return itm_cantidad;
    }

    public void setItm_cantidad(int itm_cantidad) {
        this.itm_cantidad = itm_cantidad;
    }

    public String getItm_instalacion() {
        return itm_instalacion;
    }

    public void setItm_instalacion(String itm_instalacion) {
        this.itm_instalacion = itm_instalacion;
    }

    public String getItm_estado() {
        return itm_estado;
    }

    public void setItm_estado(String itm_estado) {
        this.itm_estado = itm_estado;
    }

    public int getItm_progreso() {
        return itm_progreso;
    }

    public void setItm_progreso(int itm_progreso) {
        this.itm_progreso = itm_progreso;
    }

    public String getItm_observaciones() {
        return itm_observaciones;
    }

    public void setItm_observaciones(String itm_observaciones) {
        this.itm_observaciones = itm_observaciones;
    }

}//fin clase
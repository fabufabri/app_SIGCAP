/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.SIGCAP.modelo;

/**
 *
 * @author Fabufabri
 */
public class TipoInstalacion {

    private int tin_id;
    private String tin_codigo;
    private String tin_nombre;
    private String tin_descripcion;
    private String tin_estado;

    public TipoInstalacion() {
    }

    public TipoInstalacion(int tin_id, String tin_codigo,
                           String tin_nombre, String tin_descripcion,
                           String tin_estado) {
        this.tin_id = tin_id;
        this.tin_codigo = tin_codigo;
        this.tin_nombre = tin_nombre;
        this.tin_descripcion = tin_descripcion;
        this.tin_estado = tin_estado;
    }

    public int getTin_id() { return tin_id; }
    public void setTin_id(int tin_id) { this.tin_id = tin_id; }

    public String getTin_codigo() { return tin_codigo; }
    public void setTin_codigo(String tin_codigo) { this.tin_codigo = tin_codigo; }

    public String getTin_nombre() { return tin_nombre; }
    public void setTin_nombre(String tin_nombre) { this.tin_nombre = tin_nombre; }

    public String getTin_descripcion() { return tin_descripcion; }
    public void setTin_descripcion(String tin_descripcion) { this.tin_descripcion = tin_descripcion; }

    public String getTin_estado() { return tin_estado; }
    public void setTin_estado(String tin_estado) { this.tin_estado = tin_estado; }

}//fin clase
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.SIGCAP.modelo;

/**
 *
 * @author Fabufabri
 */
public class Producto {

    private int pro_id;
    private String pro_codigo;
    private String pro_nombre;
    private String pro_descripcion;
    private String pro_tipo;
    private String pro_estado;

    public Producto() {
    }

    public Producto(int pro_id, String pro_codigo, String pro_nombre,
                    String pro_descripcion, String pro_tipo,
                    String pro_estado) {
        this.pro_id = pro_id;
        this.pro_codigo = pro_codigo;
        this.pro_nombre = pro_nombre;
        this.pro_descripcion = pro_descripcion;
        this.pro_tipo = pro_tipo;
        this.pro_estado = pro_estado;
    }

    public int getPro_id() { return pro_id; }
    public void setPro_id(int pro_id) { this.pro_id = pro_id; }

    public String getPro_codigo() { return pro_codigo; }
    public void setPro_codigo(String pro_codigo) { this.pro_codigo = pro_codigo; }

    public String getPro_nombre() { return pro_nombre; }
    public void setPro_nombre(String pro_nombre) { this.pro_nombre = pro_nombre; }

    public String getPro_descripcion() { return pro_descripcion; }
    public void setPro_descripcion(String pro_descripcion) { this.pro_descripcion = pro_descripcion; }

    public String getPro_tipo() { return pro_tipo; }
    public void setPro_tipo(String pro_tipo) { this.pro_tipo = pro_tipo; }

    public String getPro_estado() { return pro_estado; }
    public void setPro_estado(String pro_estado) { this.pro_estado = pro_estado; }

}//fin clase

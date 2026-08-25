/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.SIGCAP.modelo;

/**
 *
 * @author Fabufabri
 */
public class Material {

    private int mat_id;
    private String mat_codigo;
    private String mat_nombre;
    private String mat_descripcion;
    private String mat_unidad;
    private String mat_estado;

    public Material() {
    }

    public Material(int mat_id, String mat_codigo, String mat_nombre,
                    String mat_descripcion, String mat_unidad,
                    String mat_estado) {
        this.mat_id = mat_id;
        this.mat_codigo = mat_codigo;
        this.mat_nombre = mat_nombre;
        this.mat_descripcion = mat_descripcion;
        this.mat_unidad = mat_unidad;
        this.mat_estado = mat_estado;
    }

    public int getMat_id() { return mat_id; }
    public void setMat_id(int mat_id) { this.mat_id = mat_id; }

    public String getMat_codigo() { return mat_codigo; }
    public void setMat_codigo(String mat_codigo) { this.mat_codigo = mat_codigo; }

    public String getMat_nombre() { return mat_nombre; }
    public void setMat_nombre(String mat_nombre) { this.mat_nombre = mat_nombre; }

    public String getMat_descripcion() { return mat_descripcion; }
    public void setMat_descripcion(String mat_descripcion) { this.mat_descripcion = mat_descripcion; }

    public String getMat_unidad() { return mat_unidad; }
    public void setMat_unidad(String mat_unidad) { this.mat_unidad = mat_unidad; }

    public String getMat_estado() { return mat_estado; }
    public void setMat_estado(String mat_estado) { this.mat_estado = mat_estado; }

}//fin clase

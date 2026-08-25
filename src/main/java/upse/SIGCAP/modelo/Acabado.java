/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.SIGCAP.modelo;

/**
 *
 * @author Fabufabri
 */
public class Acabado {

    private int aca_id;
    private String aca_codigo;
    private String aca_nombre;
    private String aca_descripcion;
    private String aca_estado;

    public Acabado() {
    }

    public Acabado(int aca_id, String aca_codigo, String aca_nombre,
                   String aca_descripcion, String aca_estado) {
        this.aca_id = aca_id;
        this.aca_codigo = aca_codigo;
        this.aca_nombre = aca_nombre;
        this.aca_descripcion = aca_descripcion;
        this.aca_estado = aca_estado;
    }

    public int getAca_id() { return aca_id; }
    public void setAca_id(int aca_id) { this.aca_id = aca_id; }

    public String getAca_codigo() { return aca_codigo; }
    public void setAca_codigo(String aca_codigo) { this.aca_codigo = aca_codigo; }

    public String getAca_nombre() { return aca_nombre; }
    public void setAca_nombre(String aca_nombre) { this.aca_nombre = aca_nombre; }

    public String getAca_descripcion() { return aca_descripcion; }
    public void setAca_descripcion(String aca_descripcion) { this.aca_descripcion = aca_descripcion; }

    public String getAca_estado() { return aca_estado; }
    public void setAca_estado(String aca_estado) { this.aca_estado = aca_estado; }

}//fin clase

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.SIGCAP.modelo;

/**
 *
 * @author Fabufabri
 */
public class EstadoProceso {

    private int est_id;
    private String est_codigo;
    private String est_nombre;
    private String est_tipo;
    private int est_orden;
    private String est_estado;

    public EstadoProceso() {
    }

    public EstadoProceso(int est_id, String est_codigo,
                         String est_nombre, String est_tipo,
                         int est_orden, String est_estado) {
        this.est_id = est_id;
        this.est_codigo = est_codigo;
        this.est_nombre = est_nombre;
        this.est_tipo = est_tipo;
        this.est_orden = est_orden;
        this.est_estado = est_estado;
    }

    public int getEst_id() { return est_id; }
    public void setEst_id(int est_id) { this.est_id = est_id; }

    public String getEst_codigo() { return est_codigo; }
    public void setEst_codigo(String est_codigo) { this.est_codigo = est_codigo; }

    public String getEst_nombre() { return est_nombre; }
    public void setEst_nombre(String est_nombre) { this.est_nombre = est_nombre; }

    public String getEst_tipo() { return est_tipo; }
    public void setEst_tipo(String est_tipo) { this.est_tipo = est_tipo; }

    public int getEst_orden() { return est_orden; }
    public void setEst_orden(int est_orden) { this.est_orden = est_orden; }

    public String getEst_estado() { return est_estado; }
    public void setEst_estado(String est_estado) { this.est_estado = est_estado; }

}//fin clase

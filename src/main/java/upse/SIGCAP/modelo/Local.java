/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.SIGCAP.modelo;

/**
 *
 * @author Fabufabri
 */
public class Local {

    private int loc_id;
    private int ciu_id;
    private String loc_codigo;
    private String loc_nombre;
    private String loc_direccion;
    private String loc_tipo;
    private String loc_estado;

    public Local() {
    }

    public Local(int loc_id, int ciu_id, String loc_codigo,
                 String loc_nombre, String loc_direccion,
                 String loc_tipo, String loc_estado) {
        this.loc_id = loc_id;
        this.ciu_id = ciu_id;
        this.loc_codigo = loc_codigo;
        this.loc_nombre = loc_nombre;
        this.loc_direccion = loc_direccion;
        this.loc_tipo = loc_tipo;
        this.loc_estado = loc_estado;
    }

    public int getLoc_id() { return loc_id; }
    public void setLoc_id(int loc_id) { this.loc_id = loc_id; }

    public int getCiu_id() { return ciu_id; }
    public void setCiu_id(int ciu_id) { this.ciu_id = ciu_id; }

    public String getLoc_codigo() { return loc_codigo; }
    public void setLoc_codigo(String loc_codigo) { this.loc_codigo = loc_codigo; }

    public String getLoc_nombre() { return loc_nombre; }
    public void setLoc_nombre(String loc_nombre) { this.loc_nombre = loc_nombre; }

    public String getLoc_direccion() { return loc_direccion; }
    public void setLoc_direccion(String loc_direccion) { this.loc_direccion = loc_direccion; }

    public String getLoc_tipo() { return loc_tipo; }
    public void setLoc_tipo(String loc_tipo) { this.loc_tipo = loc_tipo; }

    public String getLoc_estado() { return loc_estado; }
    public void setLoc_estado(String loc_estado) { this.loc_estado = loc_estado; }

}//fin clase

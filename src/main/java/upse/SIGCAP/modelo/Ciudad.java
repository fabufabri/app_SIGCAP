// src/main/java/upse/SIGCAP/modelo/Ciudad.java
package upse.SIGCAP.modelo;

public class Ciudad {

    private int ciu_id;
    private String ciu_nombre;
    private String ciu_estado;

    public Ciudad() {
    }

    public Ciudad(
            int ciu_id,
            String ciu_nombre,
            String ciu_estado) {

        this.ciu_id = ciu_id;
        this.ciu_nombre = ciu_nombre;
        this.ciu_estado = ciu_estado;
    }

    public int getCiu_id() {
        return ciu_id;
    }

    public void setCiu_id(int ciu_id) {
        this.ciu_id = ciu_id;
    }

    public String getCiu_nombre() {
        return ciu_nombre;
    }

    public void setCiu_nombre(String ciu_nombre) {
        this.ciu_nombre = ciu_nombre;
    }

    public String getCiu_estado() {
        return ciu_estado;
    }

    public void setCiu_estado(String ciu_estado) {
        this.ciu_estado = ciu_estado;
    }

}//fin clase
// src/main/java/upse/SIGCAP/modelo/Tercero.java

package upse.SIGCAP.modelo;

public class Tercero {

    private int ter_id;
    private String ter_nombre;
    private String ter_telefono;
    private String ter_correo;
    private String ter_estado;

    public Tercero() {
    }

    public Tercero(
            int ter_id,
            String ter_nombre,
            String ter_telefono,
            String ter_correo,
            String ter_estado) {

        this.ter_id = ter_id;
        this.ter_nombre = ter_nombre;
        this.ter_telefono = ter_telefono;
        this.ter_correo = ter_correo;
        this.ter_estado = ter_estado;
    }

    public int getTer_id() {
        return ter_id;
    }

    public void setTer_id(int ter_id) {
        this.ter_id = ter_id;
    }

    public String getTer_nombre() {
        return ter_nombre;
    }

    public void setTer_nombre(String ter_nombre) {
        this.ter_nombre = ter_nombre;
    }

    public String getTer_telefono() {
        return ter_telefono;
    }

    public void setTer_telefono(String ter_telefono) {
        this.ter_telefono = ter_telefono;
    }

    public String getTer_correo() {
        return ter_correo;
    }

    public void setTer_correo(String ter_correo) {
        this.ter_correo = ter_correo;
    }

    public String getTer_estado() {
        return ter_estado;
    }

    public void setTer_estado(String ter_estado) {
        this.ter_estado = ter_estado;
    }

}//fin clase
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.SIGCAP.modelo;

/**
 *
 * @author Fabufabri
 */
public class Cliente {

    private int cli_id;
    private String cli_nombre;
    private String cli_ruc;
    private String cli_contacto;
    private String cli_telefono;
    private String cli_correo;
    private String cli_estado;

    public Cliente() {
    }

    public Cliente(int cli_id, String cli_nombre, String cli_ruc,
                   String cli_contacto, String cli_telefono,
                   String cli_correo, String cli_estado) {
        this.cli_id = cli_id;
        this.cli_nombre = cli_nombre;
        this.cli_ruc = cli_ruc;
        this.cli_contacto = cli_contacto;
        this.cli_telefono = cli_telefono;
        this.cli_correo = cli_correo;
        this.cli_estado = cli_estado;
    }

    public int getCli_id() { return cli_id; }
    public void setCli_id(int cli_id) { this.cli_id = cli_id; }

    public String getCli_nombre() { return cli_nombre; }
    public void setCli_nombre(String cli_nombre) { this.cli_nombre = cli_nombre; }

    public String getCli_ruc() { return cli_ruc; }
    public void setCli_ruc(String cli_ruc) { this.cli_ruc = cli_ruc; }

    public String getCli_contacto() { return cli_contacto; }
    public void setCli_contacto(String cli_contacto) { this.cli_contacto = cli_contacto; }

    public String getCli_telefono() { return cli_telefono; }
    public void setCli_telefono(String cli_telefono) { this.cli_telefono = cli_telefono; }

    public String getCli_correo() { return cli_correo; }
    public void setCli_correo(String cli_correo) { this.cli_correo = cli_correo; }

    public String getCli_estado() { return cli_estado; }
    public void setCli_estado(String cli_estado) { this.cli_estado = cli_estado; }

}//fin clase

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.SIGCAP.modelo;

/**
 *
 * @author Fabufabri
 */
public class Usuario {

    private int usu_id;
    private String usu_usuario;
    private String usu_clave;
    private String usu_nombres;
    private String usu_apellidos;
    private String usu_estado;

    public Usuario() {
    }

    public Usuario(int usu_id, String usu_usuario, String usu_clave,
                   String usu_nombres, String usu_apellidos, String usu_estado) {
        this.usu_id = usu_id;
        this.usu_usuario = usu_usuario;
        this.usu_clave = usu_clave;
        this.usu_nombres = usu_nombres;
        this.usu_apellidos = usu_apellidos;
        this.usu_estado = usu_estado;
    }

    public int getUsu_id() {
        return usu_id;
    }

    public void setUsu_id(int usu_id) {
        this.usu_id = usu_id;
    }

    public String getUsu_usuario() {
        return usu_usuario;
    }

    public void setUsu_usuario(String usu_usuario) {
        this.usu_usuario = usu_usuario;
    }

    public String getUsu_clave() {
        return usu_clave;
    }

    public void setUsu_clave(String usu_clave) {
        this.usu_clave = usu_clave;
    }

    public String getUsu_nombres() {
        return usu_nombres;
    }

    public void setUsu_nombres(String usu_nombres) {
        this.usu_nombres = usu_nombres;
    }

    public String getUsu_apellidos() {
        return usu_apellidos;
    }

    public void setUsu_apellidos(String usu_apellidos) {
        this.usu_apellidos = usu_apellidos;
    }

    public String getUsu_estado() {
        return usu_estado;
    }

    public void setUsu_estado(String usu_estado) {
        this.usu_estado = usu_estado;
    }
}//fin clase

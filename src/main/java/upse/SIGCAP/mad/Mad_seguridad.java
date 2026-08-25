/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.SIGCAP.mad;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import upse.SIGCAP.general.BD;
import upse.SIGCAP.modelo.Usuario;

/**
 *
 * @author Fabufabri
 */
public class Mad_seguridad {

    private final BD bd;

    public Mad_seguridad() {
        bd = new BD();
    }

    public Usuario login(String usuario, String clave) {

        Usuario obj = null;

        String sql = "SELECT usu_id, usu_usuario, usu_clave, "
                + "usu_nombres, usu_apellidos, usu_estado "
                + "FROM Usuario "
                + "WHERE usu_usuario = ? "
                + "AND usu_clave = ? "
                + "AND usu_estado = 'A'";

        try {
            bd.conectarBD();

            PreparedStatement ps = bd.prepararStatement(sql);

            ps.setString(1, usuario);
            ps.setString(2, clave);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                obj = new Usuario(
                        rs.getInt("usu_id"),
                        rs.getString("usu_usuario"),
                        rs.getString("usu_clave"),
                        rs.getString("usu_nombres"),
                        rs.getString("usu_apellidos"),
                        rs.getString("usu_estado")
                );
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.err.println("Error login: " + e.getMessage());
        } finally {
            bd.desconectarBD();
        }

        return obj;
    }
}//fin clase

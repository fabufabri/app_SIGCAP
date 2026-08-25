/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package upse.SIGCAP.mad;

/**
 *
 * @author Fabufabri
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.ObservableList;
import upse.SIGCAP.general.BD;
import upse.SIGCAP.modelo.Cliente;

public class Mad_Cliente {

    private final BD bd;

    public Mad_Cliente() {
        bd = new BD();
    }

    public ObservableList<Cliente> getClientes() {
        String sql = "exec sp_getClientes";

        return bd.getListaConsulta(sql, rs -> {
            try {
                return new Cliente(
                        rs.getInt("cli_id"),
                        rs.getString("cli_nombre"),
                        rs.getString("cli_ruc"),
                        rs.getString("cli_contacto"),
                        rs.getString("cli_telefono"),
                        rs.getString("cli_correo"),
                        rs.getString("cli_estado")
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Cliente buscaClientexId(int id) {
        Cliente obj = null;

        try {
            bd.conectarBD();

            PreparedStatement ps =
                    bd.prepararStatement("exec sp_selClientexId ?");

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                obj = new Cliente(
                        rs.getInt("cli_id"),
                        rs.getString("cli_nombre"),
                        rs.getString("cli_ruc"),
                        rs.getString("cli_contacto"),
                        rs.getString("cli_telefono"),
                        rs.getString("cli_correo"),
                        rs.getString("cli_estado")
                );
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.err.println("Error Cliente: " + e.getMessage());
        } finally {
            bd.desconectarBD();
        }

        return obj;
    }

    public ObservableList<Cliente> buscarClienteLike(String texto) {
        String textoSeguro = texto == null ? "" :
                texto.replace("'", "''");

        String sql =
                "exec sp_buscar_clientelike '" + textoSeguro + "'";

        return bd.getListaConsulta(sql, rs -> {
            try {
                return new Cliente(
                        rs.getInt("cli_id"),
                        rs.getString("cli_nombre"),
                        rs.getString("cli_ruc"),
                        rs.getString("cli_contacto"),
                        rs.getString("cli_telefono"),
                        rs.getString("cli_correo"),
                        rs.getString("cli_estado")
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public boolean mantCliente(Cliente obj) {

        String sql =
                "exec sp_mantCliente "
                + obj.getCli_id() + ","
                + "'" + limpiar(obj.getCli_nombre()) + "',"
                + "'" + limpiar(obj.getCli_ruc()) + "',"
                + "'" + limpiar(obj.getCli_contacto()) + "',"
                + "'" + limpiar(obj.getCli_telefono()) + "',"
                + "'" + limpiar(obj.getCli_correo()) + "',"
                + "'" + limpiar(obj.getCli_estado()) + "'";

        return bd.fun_ejecutar(sql);
    }

    private String limpiar(String texto) {
        return texto == null ? "" :
                texto.replace("'", "''");
    }

}//fin clase

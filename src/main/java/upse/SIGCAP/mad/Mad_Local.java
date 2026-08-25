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
import upse.SIGCAP.modelo.Local;

public class Mad_Local {

    private final BD bd;

    public Mad_Local() {
        bd = new BD();
    }

    public ObservableList<Local> getLocales() {
        return bd.getListaConsulta("exec sp_getLocales", rs -> {
            try {
                return new Local(
                        rs.getInt("loc_id"),
                        rs.getInt("ciu_id"),
                        rs.getString("loc_codigo"),
                        rs.getString("loc_nombre"),
                        rs.getString("loc_direccion"),
                        rs.getString("loc_tipo"),
                        rs.getString("loc_estado")
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Local buscaLocalxId(int id) {
        Local obj = null;

        try {
            bd.conectarBD();

            PreparedStatement ps =
                    bd.prepararStatement("exec sp_selLocalxId ?");

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                obj = new Local(
                        rs.getInt("loc_id"),
                        rs.getInt("ciu_id"),
                        rs.getString("loc_codigo"),
                        rs.getString("loc_nombre"),
                        rs.getString("loc_direccion"),
                        rs.getString("loc_tipo"),
                        rs.getString("loc_estado")
                );
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.err.println("Error Local: " + e.getMessage());
        } finally {
            bd.desconectarBD();
        }

        return obj;
    }

    public boolean mantLocal(Local obj) {

        String sql =
                "exec sp_mantLocal "
                + obj.getLoc_id() + ","
                + obj.getCiu_id() + ","
                + "'" + limpiar(obj.getLoc_codigo()) + "',"
                + "'" + limpiar(obj.getLoc_nombre()) + "',"
                + "'" + limpiar(obj.getLoc_direccion()) + "',"
                + "'" + limpiar(obj.getLoc_tipo()) + "',"
                + "'" + limpiar(obj.getLoc_estado()) + "'";

        return bd.fun_ejecutar(sql);
    }

    private String limpiar(String texto) {
        return texto == null ? "" :
                texto.replace("'", "''");
    }

}//fin clase

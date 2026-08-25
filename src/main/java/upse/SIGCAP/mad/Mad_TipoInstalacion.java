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
import upse.SIGCAP.modelo.TipoInstalacion;

public class Mad_TipoInstalacion {

    private final BD bd;

    public Mad_TipoInstalacion() {
        bd = new BD();
    }

    public ObservableList<TipoInstalacion> getTiposInstalacion() {
        return bd.getListaConsulta("exec sp_getTiposInstalacion", rs -> {
            try {
                return new TipoInstalacion(
                        rs.getInt("tin_id"),
                        rs.getString("tin_codigo"),
                        rs.getString("tin_nombre"),
                        rs.getString("tin_descripcion"),
                        rs.getString("tin_estado")
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public TipoInstalacion buscaTipoInstalacionxId(int id) {
        TipoInstalacion obj = null;

        try {
            bd.conectarBD();

            PreparedStatement ps =
                    bd.prepararStatement(
                            "exec sp_selTipoInstalacionxId ?");

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                obj = new TipoInstalacion(
                        rs.getInt("tin_id"),
                        rs.getString("tin_codigo"),
                        rs.getString("tin_nombre"),
                        rs.getString("tin_descripcion"),
                        rs.getString("tin_estado")
                );
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.err.println(
                    "Error Tipo Instalación: " + e.getMessage());
        } finally {
            bd.desconectarBD();
        }

        return obj;
    }

    public boolean mantTipoInstalacion(TipoInstalacion obj) {

        String sql =
                "exec sp_mantTipoInstalacion "
                + obj.getTin_id() + ","
                + "'" + limpiar(obj.getTin_codigo()) + "',"
                + "'" + limpiar(obj.getTin_nombre()) + "',"
                + "'" + limpiar(obj.getTin_descripcion()) + "',"
                + "'" + limpiar(obj.getTin_estado()) + "'";

        return bd.fun_ejecutar(sql);
    }

    private String limpiar(String texto) {
        return texto == null ? "" :
                texto.replace("'", "''");
    }

}//fin clase

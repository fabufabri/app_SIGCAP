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
import upse.SIGCAP.modelo.EstadoProceso;

public class Mad_EstadoProceso {

    private final BD bd;

    public Mad_EstadoProceso() {
        bd = new BD();
    }

    public ObservableList<EstadoProceso> getEstadosProceso() {
        return bd.getListaConsulta("exec sp_getEstadosProceso", rs -> {
            try {
                return new EstadoProceso(
                        rs.getInt("est_id"),
                        rs.getString("est_codigo"),
                        rs.getString("est_nombre"),
                        rs.getString("est_tipo"),
                        rs.getInt("est_orden"),
                        rs.getString("est_estado")
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public EstadoProceso buscaEstadoProcesoxId(int id) {
        EstadoProceso obj = null;

        try {
            bd.conectarBD();

            PreparedStatement ps =
                    bd.prepararStatement(
                            "exec sp_selEstadoProcesoxId ?");

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                obj = new EstadoProceso(
                        rs.getInt("est_id"),
                        rs.getString("est_codigo"),
                        rs.getString("est_nombre"),
                        rs.getString("est_tipo"),
                        rs.getInt("est_orden"),
                        rs.getString("est_estado")
                );
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.err.println(
                    "Error Estado Proceso: " + e.getMessage());
        } finally {
            bd.desconectarBD();
        }

        return obj;
    }

    public boolean mantEstadoProceso(EstadoProceso obj) {

        String sql =
                "exec sp_mantEstadoProceso "
                + obj.getEst_id() + ","
                + "'" + limpiar(obj.getEst_codigo()) + "',"
                + "'" + limpiar(obj.getEst_nombre()) + "',"
                + "'" + limpiar(obj.getEst_tipo()) + "',"
                + obj.getEst_orden() + ","
                + "'" + limpiar(obj.getEst_estado()) + "'";

        return bd.fun_ejecutar(sql);
    }

    private String limpiar(String texto) {
        return texto == null ? "" :
                texto.replace("'", "''");
    }

}//fin clase

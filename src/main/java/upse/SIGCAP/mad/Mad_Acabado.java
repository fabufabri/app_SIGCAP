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
import upse.SIGCAP.modelo.Acabado;

public class Mad_Acabado {

    private final BD bd;

    public Mad_Acabado() {
        bd = new BD();
    }

    public ObservableList<Acabado> getAcabados() {
        return bd.getListaConsulta("exec sp_getAcabados", rs -> {
            try {
                return new Acabado(
                        rs.getInt("aca_id"),
                        rs.getString("aca_codigo"),
                        rs.getString("aca_nombre"),
                        rs.getString("aca_descripcion"),
                        rs.getString("aca_estado")
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Acabado buscaAcabadoxId(int id) {
        Acabado obj = null;

        try {
            bd.conectarBD();

            PreparedStatement ps =
                    bd.prepararStatement("exec sp_selAcabadoxId ?");

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                obj = new Acabado(
                        rs.getInt("aca_id"),
                        rs.getString("aca_codigo"),
                        rs.getString("aca_nombre"),
                        rs.getString("aca_descripcion"),
                        rs.getString("aca_estado")
                );
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.err.println("Error Acabado: " + e.getMessage());
        } finally {
            bd.desconectarBD();
        }

        return obj;
    }

    public boolean mantAcabado(Acabado obj) {

        String sql =
                "exec sp_mantAcabado "
                + obj.getAca_id() + ","
                + "'" + limpiar(obj.getAca_codigo()) + "',"
                + "'" + limpiar(obj.getAca_nombre()) + "',"
                + "'" + limpiar(obj.getAca_descripcion()) + "',"
                + "'" + limpiar(obj.getAca_estado()) + "'";

        return bd.fun_ejecutar(sql);
    }

    private String limpiar(String texto) {
        return texto == null ? "" :
                texto.replace("'", "''");
    }

}//fin clase

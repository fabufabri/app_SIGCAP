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
import upse.SIGCAP.modelo.Medida;

public class Mad_Medida {

    private final BD bd;

    public Mad_Medida() {
        bd = new BD();
    }

    public ObservableList<Medida> getMedidas() {
        return bd.getListaConsulta("exec sp_getMedidas", rs -> {
            try {
                return new Medida(
                        rs.getInt("med_id"),
                        rs.getString("med_nombre"),
                        rs.getDouble("med_ancho"),
                        rs.getDouble("med_alto"),
                        rs.getString("med_unidad"),
                        rs.getString("med_tipo"),
                        rs.getString("med_estado")
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Medida buscaMedidaxId(int id) {
        Medida obj = null;

        try {
            bd.conectarBD();

            PreparedStatement ps =
                    bd.prepararStatement("exec sp_selMedidaxId ?");

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                obj = new Medida(
                        rs.getInt("med_id"),
                        rs.getString("med_nombre"),
                        rs.getDouble("med_ancho"),
                        rs.getDouble("med_alto"),
                        rs.getString("med_unidad"),
                        rs.getString("med_tipo"),
                        rs.getString("med_estado")
                );
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.err.println("Error Medida: " + e.getMessage());
        } finally {
            bd.desconectarBD();
        }

        return obj;
    }

    public boolean mantMedida(Medida obj) {

        String sql =
                "exec sp_mantMedida "
                + obj.getMed_id() + ","
                + "'" + limpiar(obj.getMed_nombre()) + "',"
                + obj.getMed_ancho() + ","
                + obj.getMed_alto() + ","
                + "'" + limpiar(obj.getMed_unidad()) + "',"
                + "'" + limpiar(obj.getMed_tipo()) + "',"
                + "'" + limpiar(obj.getMed_estado()) + "'";

        return bd.fun_ejecutar(sql);
    }

    private String limpiar(String texto) {
        return texto == null ? "" :
                texto.replace("'", "''");
    }

}//fin clase

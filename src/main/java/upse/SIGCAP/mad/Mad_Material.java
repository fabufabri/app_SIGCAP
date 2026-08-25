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
import upse.SIGCAP.modelo.Material;

public class Mad_Material {

    private final BD bd;

    public Mad_Material() {
        bd = new BD();
    }

    public ObservableList<Material> getMateriales() {
        return bd.getListaConsulta("exec sp_getMateriales", rs -> {
            try {
                return new Material(
                        rs.getInt("mat_id"),
                        rs.getString("mat_codigo"),
                        rs.getString("mat_nombre"),
                        rs.getString("mat_descripcion"),
                        rs.getString("mat_unidad"),
                        rs.getString("mat_estado")
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Material buscaMaterialxId(int id) {
        Material obj = null;

        try {
            bd.conectarBD();

            PreparedStatement ps =
                    bd.prepararStatement("exec sp_selMaterialxId ?");

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                obj = new Material(
                        rs.getInt("mat_id"),
                        rs.getString("mat_codigo"),
                        rs.getString("mat_nombre"),
                        rs.getString("mat_descripcion"),
                        rs.getString("mat_unidad"),
                        rs.getString("mat_estado")
                );
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.err.println("Error Material: " + e.getMessage());
        } finally {
            bd.desconectarBD();
        }

        return obj;
    }

    public boolean mantMaterial(Material obj) {

        String sql =
                "exec sp_mantMaterial "
                + obj.getMat_id() + ","
                + "'" + limpiar(obj.getMat_codigo()) + "',"
                + "'" + limpiar(obj.getMat_nombre()) + "',"
                + "'" + limpiar(obj.getMat_descripcion()) + "',"
                + "'" + limpiar(obj.getMat_unidad()) + "',"
                + "'" + limpiar(obj.getMat_estado()) + "'";

        return bd.fun_ejecutar(sql);
    }

    private String limpiar(String texto) {
        return texto == null ? "" :
                texto.replace("'", "''");
    }

}//fin clase

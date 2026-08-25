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
import upse.SIGCAP.modelo.Producto;

public class Mad_Producto {

    private final BD bd;

    public Mad_Producto() {
        bd = new BD();
    }

    public ObservableList<Producto> getProductos() {
        return bd.getListaConsulta("exec sp_getProductos", rs -> {
            try {
                return new Producto(
                        rs.getInt("pro_id"),
                        rs.getString("pro_codigo"),
                        rs.getString("pro_nombre"),
                        rs.getString("pro_descripcion"),
                        rs.getString("pro_tipo"),
                        rs.getString("pro_estado")
                );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Producto buscaProductoxId(int id) {
        Producto obj = null;

        try {
            bd.conectarBD();

            PreparedStatement ps =
                    bd.prepararStatement("exec sp_selProductoxId ?");

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                obj = new Producto(
                        rs.getInt("pro_id"),
                        rs.getString("pro_codigo"),
                        rs.getString("pro_nombre"),
                        rs.getString("pro_descripcion"),
                        rs.getString("pro_tipo"),
                        rs.getString("pro_estado")
                );
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            System.err.println("Error Producto: " + e.getMessage());
        } finally {
            bd.desconectarBD();
        }

        return obj;
    }

    public ObservableList<Producto> buscarProductoLike(String texto) {
        String valor = texto == null ? "" :
                texto.replace("'", "''");

        return bd.getListaConsulta(
                "exec sp_buscar_productolike '" + valor + "'",
                rs -> {
                    try {
                        return new Producto(
                                rs.getInt("pro_id"),
                                rs.getString("pro_codigo"),
                                rs.getString("pro_nombre"),
                                rs.getString("pro_descripcion"),
                                rs.getString("pro_tipo"),
                                rs.getString("pro_estado")
                        );
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public boolean mantProducto(Producto obj) {

        String sql =
                "exec sp_mantProducto "
                + obj.getPro_id() + ","
                + "'" + limpiar(obj.getPro_codigo()) + "',"
                + "'" + limpiar(obj.getPro_nombre()) + "',"
                + "'" + limpiar(obj.getPro_descripcion()) + "',"
                + "'" + limpiar(obj.getPro_tipo()) + "',"
                + "'" + limpiar(obj.getPro_estado()) + "'";

        return bd.fun_ejecutar(sql);
    }

    private String limpiar(String texto) {
        return texto == null ? "" :
                texto.replace("'", "''");
    }

}//fin clase

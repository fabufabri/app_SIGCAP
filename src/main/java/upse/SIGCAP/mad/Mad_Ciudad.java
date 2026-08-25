// src/main/java/upse/SIGCAP/mad/Mad_Ciudad.java
// VERIFICACIÓN DE BD:
// Este MAD utiliza:
//
// bd.conectarBD()
// bd.getConexion()
// bd.getListaConsulta()
// bd.ejecutarSQL()
// bd.desconectarBD()
//
// Por lo tanto no requiere cambios en BD.java,
// siempre que tu BD actual tenga getConexion() público.
package upse.SIGCAP.mad;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.ObservableList;
import upse.SIGCAP.general.BD;
import upse.SIGCAP.modelo.Ciudad;

public class Mad_Ciudad {

    private final BD bd;

    public Mad_Ciudad() {
        bd = new BD();
    }

    public ObservableList<Ciudad> getCiudades() {

        String sql = "exec sp_getCiudades";

        return bd.getListaConsulta(
                sql,
                rs -> {

                    try {

                        return new Ciudad(
                                rs.getInt("ciu_id"),
                                rs.getString("ciu_nombre"),
                                rs.getString("ciu_estado")
                        );

                    } catch (SQLException e) {

                        throw new RuntimeException(e);
                    }
                }
        );
    }

    public Ciudad buscaCiudadxId(int id) {

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            bd.conectarBD();

            ps = bd.getConexion()
                    .prepareStatement(
                            "exec sp_selCiudadxid ?"
                    );

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {

                return new Ciudad(
                        rs.getInt("ciu_id"),
                        rs.getString("ciu_nombre"),
                        rs.getString("ciu_estado")
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error buscaCiudadxId: "
                    + e.getMessage()
            );

        } finally {

            bd.desconectarBD();
        }

        return null;
    }

    public boolean mantCiudad(Ciudad obj) {

        String nombre =
                obj.getCiu_nombre() == null
                        ? ""
                        : obj.getCiu_nombre()
                                .replace("'", "''");

        String estado =
                obj.getCiu_estado() == null
                        ? "A"
                        : obj.getCiu_estado()
                                .replace("'", "''");

        String sql =
                "exec sp_mantCiudad "
                + obj.getCiu_id()
                + ",'"
                + nombre
                + "','"
                + estado
                + "'";

        try {

            bd.conectarBD();

            return bd.ejecutarSQL(sql) > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error mantCiudad: "
                    + e.getMessage()
            );

            return false;

        } finally {

            bd.desconectarBD();
        }
    }

}//fin clase
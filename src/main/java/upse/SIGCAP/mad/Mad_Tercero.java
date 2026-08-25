// src/main/java/upse/SIGCAP/mad/Mad_Tercero.java

package upse.SIGCAP.mad;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.ObservableList;

import upse.SIGCAP.general.BD;
import upse.SIGCAP.modelo.Tercero;

public class Mad_Tercero {

    private final BD bd;

    public Mad_Tercero() {
        bd = new BD();
    }

    public ObservableList<Tercero> getTerceros() {

        String sql =
                "exec sp_getTerceros";

        return bd.getListaConsulta(
                sql,
                rs -> mapear(rs)
        );
    }

    public Tercero buscaTerceroxId(int id) {

        String sql =
                "exec sp_selTerceroxid ?";

        Tercero obj = null;

        try {

            bd.conectarBD();

            PreparedStatement ps =
                    bd.getConexion()
                            .prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                obj = mapear(rs);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            bd.desconectarBD();
        }

        return obj;
    }

    public ObservableList<Tercero> buscarTerceroLike(
            String texto) {

        String textoSeguro =
                texto == null
                        ? ""
                        : texto.replace("'", "''");

        String sql =
                "exec sp_buscar_tercerolike '"
                + textoSeguro
                + "'";

        return bd.getListaConsulta(
                sql,
                rs -> mapear(rs)
        );
    }

    public boolean mantTercero(Tercero obj) {

        String nombre =
                escapar(obj.getTer_nombre());

        String telefono =
                escapar(obj.getTer_telefono());

        String correo =
                escapar(obj.getTer_correo());

        String estado =
                escapar(obj.getTer_estado());

        String sql =
                "exec sp_mantTercero "
                + obj.getTer_id()
                + ",'"
                + nombre
                + "','"
                + telefono
                + "','"
                + correo
                + "','"
                + estado
                + "'";

        try {

            bd.conectarBD();

            return bd.ejecutarSQL(sql) > 0;

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        } finally {

            bd.desconectarBD();
        }
    }

    private Tercero mapear(
            ResultSet rs) {

        try {

            Tercero obj =
                    new Tercero();

            obj.setTer_id(
                    rs.getInt("ter_id")
            );

            obj.setTer_nombre(
                    rs.getString("ter_nombre")
            );

            obj.setTer_telefono(
                    rs.getString("ter_telefono")
            );

            obj.setTer_correo(
                    rs.getString("ter_correo")
            );

            obj.setTer_estado(
                    rs.getString("ter_estado")
            );

            return obj;

        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    private String escapar(
            String valor) {

        if (valor == null) {
            return "";
        }

        return valor.replace(
                "'",
                "''"
        );
    }

}//fin clase
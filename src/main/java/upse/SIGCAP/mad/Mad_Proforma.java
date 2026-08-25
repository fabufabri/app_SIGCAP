// src/main/java/upse/SIGCAP/mad/Mad_Proforma.java

package upse.SIGCAP.mad;

import java.sql.ResultSet;
import javafx.collections.ObservableList;
import upse.SIGCAP.general.BD;
import upse.SIGCAP.modelo.Proforma;

public class Mad_Proforma {

    private final BD bd;

    public Mad_Proforma() {
        bd = new BD();
    }

    public ObservableList<Proforma> getProformas() {

        return bd.getListaConsulta(
                "exec sp_getProformas",
                rs -> mapearProforma(rs)
        );
    }

    public Proforma buscaProformaxId(int id) {

        ObservableList<Proforma> lista =
                bd.getListaConsulta(
                        "exec sp_selProformaxid " + id,
                        rs -> mapearProforma(rs)
                );

        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }

        return null;
    }

    public boolean mantProforma(Proforma obj) {

        String camId =
                obj.getCam_id() == null
                        ? "NULL"
                        : obj.getCam_id().toString();

        String validez =
                obj.getPfr_validez() == null
                        ? "NULL"
                        : "'" + obj.getPfr_validez() + "'";

        String sql =
                "exec sp_mantProforma "
                + obj.getPfr_id() + ","
                + obj.getCli_id() + ","
                + camId + ","
                + "'" + limpiar(obj.getPfr_codigo()) + "',"
                + "'" + obj.getPfr_fecha() + "',"
                + validez + ","
                + obj.getPfr_subtotal() + ","
                + obj.getPfr_iva() + ","
                + obj.getPfr_total() + ","
                + "'" + limpiar(obj.getPfr_estado()) + "',"
                + valorTexto(obj.getPfr_observaciones());

        return bd.fun_ejecutar(sql);
    }

    private Proforma mapearProforma(ResultSet rs) {

        try {

            Proforma obj = new Proforma();

            obj.setPfr_id(rs.getInt("pfr_id"));
            obj.setPfr_codigo(rs.getString("pfr_codigo"));

            if (rs.getDate("pfr_fecha") != null) {
                obj.setPfr_fecha(
                        rs.getDate("pfr_fecha").toLocalDate()
                );
            }

            if (rs.getDate("pfr_validez") != null) {
                obj.setPfr_validez(
                        rs.getDate("pfr_validez").toLocalDate()
                );
            }

            obj.setCli_id(rs.getInt("cli_id"));
            obj.setCam_id(
                    (Integer) rs.getObject("cam_id")
            );

            obj.setCli_nombre(
                    rs.getString("cli_nombre")
            );

            obj.setCam_nombre(
                    rs.getString("cam_nombre")
            );

            obj.setPfr_subtotal(
                    rs.getDouble("pfr_subtotal")
            );

            obj.setPfr_iva(
                    rs.getDouble("pfr_iva")
            );

            obj.setPfr_total(
                    rs.getDouble("pfr_total")
            );

            obj.setPfr_estado(
                    rs.getString("pfr_estado")
            );

            obj.setPfr_observaciones(
                    rs.getString("pfr_observaciones")
            );

            return obj;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error mapeando Proforma: "
                    + e.getMessage(),
                    e
            );
        }
    }

    private String limpiar(String texto) {

        if (texto == null) {
            return "";
        }

        return texto.replace("'", "''");
    }

    private String valorTexto(String texto) {

        if (texto == null || texto.trim().isEmpty()) {
            return "NULL";
        }

        return "'" + limpiar(texto) + "'";
    }

}//fin clase
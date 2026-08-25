// src/main/java/upse/SIGCAP/mad/Mad_Factura.java

package upse.SIGCAP.mad;

import java.sql.ResultSet;
import javafx.collections.ObservableList;
import upse.SIGCAP.general.BD;
import upse.SIGCAP.modelo.Factura;

public class Mad_Factura {

    private final BD bd;

    public Mad_Factura() {
        bd = new BD();
    }

    public ObservableList<Factura> getFacturas() {

        return bd.getListaConsulta(
                "exec sp_getFacturas",
                rs -> mapearFactura(rs)
        );
    }

    public Factura buscaFacturaxId(int id) {

        ObservableList<Factura> lista =
                bd.getListaConsulta(
                        "exec sp_selFacturaxid " + id,
                        rs -> mapearFactura(rs)
                );

        if (lista != null && !lista.isEmpty()) {
            return lista.get(0);
        }

        return null;
    }

    public boolean mantFactura(Factura obj) {

        String pfrId =
                obj.getPfr_id() == null
                        ? "NULL"
                        : obj.getPfr_id().toString();

        String otId =
                obj.getOt_id() == null
                        ? "NULL"
                        : obj.getOt_id().toString();

        String sql =
                "exec sp_mantFactura "
                + obj.getFac_id() + ","
                + obj.getCli_id() + ","
                + pfrId + ","
                + otId + ","
                + "'" + limpiar(obj.getFac_numero()) + "',"
                + "'" + obj.getFac_fecha() + "',"
                + obj.getFac_subtotal() + ","
                + obj.getFac_iva() + ","
                + obj.getFac_total() + ","
                + "'" + limpiar(obj.getFac_estado()) + "',"
                + valorTexto(obj.getFac_observaciones());

        return bd.fun_ejecutar(sql);
    }

    private Factura mapearFactura(ResultSet rs) {

        try {

            Factura obj = new Factura();

            obj.setFac_id(
                    rs.getInt("fac_id")
            );

            obj.setFac_numero(
                    rs.getString("fac_numero")
            );

            if (rs.getDate("fac_fecha") != null) {

                obj.setFac_fecha(
                        rs.getDate("fac_fecha").toLocalDate()
                );
            }

            obj.setCli_id(
                    rs.getInt("cli_id")
            );

            obj.setPfr_id(
                    (Integer) rs.getObject("pfr_id")
            );

            obj.setOt_id(
                    (Integer) rs.getObject("ot_id")
            );

            obj.setCli_nombre(
                    rs.getString("cli_nombre")
            );

            obj.setPfr_codigo(
                    rs.getString("pfr_codigo")
            );

            obj.setOt_codigo(
                    rs.getString("ot_codigo")
            );

            obj.setFac_subtotal(
                    rs.getDouble("fac_subtotal")
            );

            obj.setFac_iva(
                    rs.getDouble("fac_iva")
            );

            obj.setFac_total(
                    rs.getDouble("fac_total")
            );

            obj.setFac_estado(
                    rs.getString("fac_estado")
            );

            obj.setFac_observaciones(
                    rs.getString("fac_observaciones")
            );

            return obj;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error mapeando Factura: "
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
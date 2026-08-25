// src/main/java/upse/SIGCAP/mad/Mad_DetalleFactura.java

package upse.SIGCAP.mad;

import java.sql.ResultSet;
import javafx.collections.ObservableList;
import upse.SIGCAP.general.BD;
import upse.SIGCAP.modelo.DetalleFactura;

public class Mad_DetalleFactura {

    private final BD bd;

    public Mad_DetalleFactura() {
        bd = new BD();
    }

    public ObservableList<DetalleFactura> getDetalles(int facId) {

        return bd.getListaConsulta(
                "exec sp_getDetalleFacturaxFactura " + facId,
                rs -> mapearDetalle(rs)
        );
    }

    public boolean mantDetalleFactura(
            DetalleFactura obj) {

        String sql =
                "exec sp_mantDetalleFactura "
                + obj.getDfa_id() + ","
                + obj.getFac_id() + ","
                + "'" + limpiar(obj.getDfa_descripcion()) + "',"
                + obj.getDfa_cantidad() + ","
                + obj.getDfa_precio_unitario() + ","
                + obj.getDfa_descuento() + ","
                + obj.getDfa_total();

        return bd.fun_ejecutar(sql);
    }

    public boolean eliminarDetalle(int id) {

        return bd.fun_ejecutar(
                "exec sp_eliminarDetalleFactura " + id
        );
    }

    private DetalleFactura mapearDetalle(ResultSet rs) {

        try {

            DetalleFactura obj =
                    new DetalleFactura();

            obj.setDfa_id(
                    rs.getInt("dfa_id")
            );

            obj.setFac_id(
                    rs.getInt("fac_id")
            );

            obj.setDfa_descripcion(
                    rs.getString("dfa_descripcion")
            );

            obj.setDfa_cantidad(
                    rs.getInt("dfa_cantidad")
            );

            obj.setDfa_precio_unitario(
                    rs.getDouble("dfa_precio_unitario")
            );

            obj.setDfa_descuento(
                    rs.getDouble("dfa_descuento")
            );

            obj.setDfa_total(
                    rs.getDouble("dfa_total")
            );

            return obj;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error mapeando DetalleFactura: "
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

}//fin clase
// src/main/java/upse/SIGCAP/mad/Mad_DetalleProforma.java

package upse.SIGCAP.mad;

import java.sql.ResultSet;
import javafx.collections.ObservableList;
import upse.SIGCAP.general.BD;
import upse.SIGCAP.modelo.DetalleProforma;

public class Mad_DetalleProforma {

    private final BD bd;

    public Mad_DetalleProforma() {
        bd = new BD();
    }

    public ObservableList<DetalleProforma> getDetalles(int pfrId) {

        return bd.getListaConsulta(
                "exec sp_getDetalleProformaxProforma " + pfrId,
                rs -> mapearDetalle(rs)
        );
    }

    public boolean mantDetalleProforma(
            DetalleProforma obj) {

        String proId =
                obj.getPro_id() == null
                        ? "NULL"
                        : obj.getPro_id().toString();

        String medId =
                obj.getMed_id() == null
                        ? "NULL"
                        : obj.getMed_id().toString();

        String matId =
                obj.getMat_id() == null
                        ? "NULL"
                        : obj.getMat_id().toString();

        String sql =
                "exec sp_mantDetalleProforma "
                + obj.getDpf_id() + ","
                + obj.getPfr_id() + ","
                + proId + ","
                + medId + ","
                + matId + ","
                + "'" + limpiar(obj.getDpf_descripcion()) + "',"
                + obj.getDpf_cantidad() + ","
                + obj.getDpf_precio_unitario() + ","
                + obj.getDpf_descuento() + ","
                + obj.getDpf_total();

        return bd.fun_ejecutar(sql);
    }

    public boolean eliminarDetalle(int id) {

        return bd.fun_ejecutar(
                "exec sp_eliminarDetalleProforma " + id
        );
    }

    private DetalleProforma mapearDetalle(ResultSet rs) {

        try {

            DetalleProforma obj =
                    new DetalleProforma();

            obj.setDpf_id(
                    rs.getInt("dpf_id")
            );

            obj.setPfr_id(
                    rs.getInt("pfr_id")
            );

            obj.setPro_id(
                    (Integer) rs.getObject("pro_id")
            );

            obj.setMed_id(
                    (Integer) rs.getObject("med_id")
            );

            obj.setMat_id(
                    (Integer) rs.getObject("mat_id")
            );

            obj.setPro_nombre(
                    rs.getString("pro_nombre")
            );

            obj.setMed_nombre(
                    rs.getString("med_nombre")
            );

            obj.setMat_nombre(
                    rs.getString("mat_nombre")
            );

            obj.setDpf_descripcion(
                    rs.getString("dpf_descripcion")
            );

            obj.setDpf_cantidad(
                    rs.getInt("dpf_cantidad")
            );

            obj.setDpf_precio_unitario(
                    rs.getDouble("dpf_precio_unitario")
            );

            obj.setDpf_descuento(
                    rs.getDouble("dpf_descuento")
            );

            obj.setDpf_total(
                    rs.getDouble("dpf_total")
            );

            return obj;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error mapeando DetalleProforma: "
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
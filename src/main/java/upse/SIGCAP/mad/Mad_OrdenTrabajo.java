// src/main/java/upse/SIGCAP/mad/Mad_OrdenTrabajo.java

package upse.SIGCAP.mad;

import java.sql.ResultSet;

import javafx.collections.ObservableList;

import upse.SIGCAP.general.BD;
import upse.SIGCAP.modelo.ItemOrdenTrabajo;
import upse.SIGCAP.modelo.OrdenTrabajo;

public class Mad_OrdenTrabajo {

    private final BD bd;

    public Mad_OrdenTrabajo() {

        bd = new BD();
    }

    // =========================================================
    // LISTADO GENERAL DE ÓRDENES DE TRABAJO
    // =========================================================

    public ObservableList<OrdenTrabajo> getOrdenesTrabajo() {

        String sql =
                "exec sp_getOrdenesTrabajo";

        return bd.getListaConsulta(
                sql,
                rs -> mapearOrden(rs)
        );
    }

    // Alias utilizado por algunos módulos anteriores

    public ObservableList<OrdenTrabajo> getOrdenTrabajos() {

        return getOrdenesTrabajo();
    }

    // =========================================================
    // BUSCAR ORDEN DE TRABAJO POR ID
    // =========================================================

    public OrdenTrabajo buscaOrdenTrabajoxId(
            int id) {

        String sql =
                "exec sp_selOrdenTrabajoxid "
                + id;

        try {

            ObservableList<OrdenTrabajo> lista =
                    bd.getListaConsulta(
                            sql,
                            rs -> mapearOrden(rs)
                    );

            if (lista != null
                    && !lista.isEmpty()) {

                return lista.get(0);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return null;
    }

    // =========================================================
    // ITEMS DE UNA ORDEN
    // =========================================================

    public ObservableList<ItemOrdenTrabajo> getItems(
            int otId) {

        String sql =
                "exec sp_getItemsOrdenTrabajo "
                + otId;

        return bd.getListaConsulta(
                sql,
                rs -> mapearItem(rs)
        );
    }

    // =========================================================
    // MAPEAR ORDEN DE TRABAJO
    // =========================================================

    private OrdenTrabajo mapearOrden(
            ResultSet rs) {

        try {

            OrdenTrabajo obj =
                    new OrdenTrabajo();

            obj.setOt_id(
                    rs.getInt("ot_id")
            );

            obj.setOt_codigo(
                    rs.getString("ot_codigo")
            );

            obj.setOt_cliente(
                    rs.getString("ot_cliente")
            );

            obj.setOt_campania(
                    rs.getString("ot_campania")
            );

            obj.setOt_solicitante(
                    rs.getString("ot_solicitante")
            );

            obj.setOt_ciudad(
                    rs.getString("ot_ciudad")
            );

            if (rs.getDate("ot_fecha") != null) {

                obj.setOt_fecha(
                        rs.getDate(
                                "ot_fecha"
                        ).toLocalDate()
                );

            } else {

                obj.setOt_fecha(null);
            }

            if (rs.getDate("ot_fecha_requerida") != null) {

                obj.setOt_fecha_requerida(
                        rs.getDate(
                                "ot_fecha_requerida"
                        ).toLocalDate()
                );

            } else {

                obj.setOt_fecha_requerida(null);
            }

            obj.setOt_prioridad(
                    rs.getString("ot_prioridad")
            );

            obj.setOt_responsable(
                    rs.getString("ot_responsable")
            );

            obj.setOt_observaciones(
                    rs.getString("ot_observaciones")
            );

            obj.setOt_estado(
                    rs.getString("ot_estado")
            );

            return obj;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error mapeando OrdenTrabajo: "
                    + e.getMessage(),
                    e
            );
        }
    }

    // =========================================================
    // MAPEAR ITEM DE ORDEN
    // =========================================================

    private ItemOrdenTrabajo mapearItem(
            ResultSet rs) {

        try {

            ItemOrdenTrabajo obj =
                    new ItemOrdenTrabajo();

            obj.setItm_id(
                    rs.getInt("itm_id")
            );

            obj.setItm_local(
                    rs.getString("itm_local")
            );

            obj.setItm_producto(
                    rs.getString("itm_producto")
            );

            obj.setItm_descripcion(
                    rs.getString("itm_descripcion")
            );

            obj.setItm_medida(
                    rs.getString("itm_medida")
            );

            obj.setItm_material(
                    rs.getString("itm_material")
            );

            obj.setItm_cantidad(
                    rs.getInt("itm_cantidad")
            );

            obj.setItm_instalacion(
                    rs.getString("itm_instalacion")
            );

            obj.setItm_estado(
                    rs.getString("itm_estado")
            );

            obj.setItm_progreso(
                    rs.getInt("itm_progreso")
            );

            return obj;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Error mapeando ItemOrdenTrabajo: "
                    + e.getMessage(),
                    e
            );
        }
    }

}//fin clase
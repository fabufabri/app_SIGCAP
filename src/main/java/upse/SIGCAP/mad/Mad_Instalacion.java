package upse.SIGCAP.mad;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.ObservableList;

import upse.SIGCAP.general.BD;
import upse.SIGCAP.modelo.Instalacion;

public class Mad_Instalacion {

    private final BD bd;

    public Mad_Instalacion() {
        bd = new BD();
    }

    public ObservableList<Instalacion> getInstalaciones() {

        String sql =
                "exec sp_getInstalaciones";

        return bd.getListaConsulta(
                sql,
                rs -> {

                    try {

                        Instalacion obj =
                                new Instalacion();

                        obj.setIns_id(
                                rs.getInt("ins_id")
                        );

                        obj.setItm_id(
                                rs.getInt("itm_id")
                        );

                        obj.setTer_id(
                                rs.getInt("ter_id")
                        );

                        obj.setTin_id(
                                rs.getInt("tin_id")
                        );

                        if (rs.getDate(
                                "ins_fecha_programada"
                        ) != null) {

                            obj.setIns_fecha_programada(
                                    rs.getDate(
                                            "ins_fecha_programada"
                                    ).toLocalDate()
                            );
                        }

                        if (rs.getDate(
                                "ins_fecha_real"
                        ) != null) {

                            obj.setIns_fecha_real(
                                    rs.getDate(
                                            "ins_fecha_real"
                                    ).toLocalDate()
                            );
                        }

                        obj.setIns_estado(
                                rs.getString(
                                        "ins_estado"
                                )
                        );

                        obj.setIns_observaciones(
                                rs.getString(
                                        "ins_observaciones"
                                )
                        );

                        obj.setIns_evidencia(
                                rs.getString(
                                        "ins_evidencia"
                                )
                        );

                        return obj;

                    } catch (SQLException e) {

                        throw new RuntimeException(e);
                    }
                }
        );
    }

    public Instalacion buscaInstalacionxId(
            int id) {

        String sql =
                "exec sp_selInstalacionxId ?";

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            bd.conectarBD();

            ps =
                    bd.getConexion()
                            .prepareStatement(sql);

            ps.setInt(
                    1,
                    id
            );

            rs = ps.executeQuery();

            if (rs.next()) {

                Instalacion obj =
                        new Instalacion();

                obj.setIns_id(
                        rs.getInt("ins_id")
                );

                obj.setItm_id(
                        rs.getInt("itm_id")
                );

                obj.setTer_id(
                        rs.getInt("ter_id")
                );

                obj.setTin_id(
                        rs.getInt("tin_id")
                );

                if (rs.getDate(
                        "ins_fecha_programada"
                ) != null) {

                    obj.setIns_fecha_programada(
                            rs.getDate(
                                    "ins_fecha_programada"
                            ).toLocalDate()
                    );
                }

                if (rs.getDate(
                        "ins_fecha_real"
                ) != null) {

                    obj.setIns_fecha_real(
                            rs.getDate(
                                    "ins_fecha_real"
                            ).toLocalDate()
                    );
                }

                obj.setIns_estado(
                        rs.getString(
                                "ins_estado"
                        )
                );

                obj.setIns_observaciones(
                        rs.getString(
                                "ins_observaciones"
                        )
                );

                obj.setIns_evidencia(
                        rs.getString(
                                "ins_evidencia"
                        )
                );

                return obj;
            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            bd.desconectarBD();
        }

        return null;
    }

    public boolean mantInstalacion(
            Instalacion obj) {

        String fechaProgramada =
                obj.getIns_fecha_programada()
                        == null
                ? "NULL"
                : "'"
                + obj.getIns_fecha_programada()
                        .toString()
                + "'";

        String fechaReal =
                obj.getIns_fecha_real()
                        == null
                ? "NULL"
                : "'"
                + obj.getIns_fecha_real()
                        .toString()
                + "'";

        String observaciones =
                obj.getIns_observaciones()
                        == null
                ? "NULL"
                : "'"
                + obj.getIns_observaciones()
                        .replace("'", "''")
                + "'";

        String evidencia =
                obj.getIns_evidencia()
                        == null
                ? "NULL"
                : "'"
                + obj.getIns_evidencia()
                        .replace("'", "''")
                + "'";

        String estado =
                obj.getIns_estado()
                        == null
                ? "'PENDIENTE'"
                : "'"
                + obj.getIns_estado()
                        .replace("'", "''")
                + "'";

        String sql =
                "exec sp_mantInstalacion "
                + obj.getIns_id()
                + ","
                + obj.getItm_id()
                + ","
                + obj.getTer_id()
                + ","
                + obj.getTin_id()
                + ","
                + fechaProgramada
                + ","
                + fechaReal
                + ","
                + estado
                + ","
                + observaciones
                + ","
                + evidencia;

        try {

            bd.conectarBD();

            return bd.fun_ejecutar(
                    sql
            );

        } catch (Exception e) {

            e.printStackTrace();

            return false;

        } finally {

            bd.desconectarBD();
        }
    }
}
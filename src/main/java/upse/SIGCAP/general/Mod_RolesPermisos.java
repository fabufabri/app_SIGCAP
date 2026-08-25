// src/main/java/upse/SIGCAP/general/Mod_RolesPermisos.java

package upse.SIGCAP.general;

import java.util.HashMap;
import java.util.Map;

public class Mod_RolesPermisos {

    public static final String ADMINISTRADOR = "ADMINISTRADOR";
    public static final String COORDINADOR = "COORDINADOR";
    public static final String DISENO = "DISEÑO";
    public static final String PRODUCCION = "PRODUCCIÓN";
    public static final String INSTALACIONES = "INSTALACIONES";
    public static final String CONSULTA = "CONSULTA";

    private static final Map<String, Map<String, Boolean>> permisos =
            new HashMap<>();

    static {

        inicializarRol(
                ADMINISTRADOR,
                true
        );

        inicializarRol(
                COORDINADOR,
                true
        );

        inicializarRol(
                DISENO,
                false
        );

        inicializarRol(
                PRODUCCION,
                false
        );

        inicializarRol(
                INSTALACIONES,
                false
        );

        inicializarRol(
                CONSULTA,
                false
        );

        configurarPermisos();
    }

    private static void inicializarRol(
            String rol,
            boolean completo) {

        Map<String, Boolean> mapa =
                new HashMap<>();

        mapa.put("dashboard", true);
        mapa.put("nueva_ot", completo);
        mapa.put("buscar_ot", true);
        mapa.put("mis_ot", true);
        mapa.put("produccion", completo);
        mapa.put("artes", completo);
        mapa.put("terceros", completo);
        mapa.put("instalaciones", completo);
        mapa.put("catalogos", completo);
        mapa.put("reportes", true);
        mapa.put("configuracion", completo);
        mapa.put("usuarios", completo);
        mapa.put("roles", completo);

        permisos.put(
                rol,
                mapa
        );
    }

    private static void configurarPermisos() {

        Map<String, Boolean> diseno =
                permisos.get(DISENO);

        diseno.put("nueva_ot", false);
        diseno.put("buscar_ot", true);
        diseno.put("mis_ot", true);
        diseno.put("produccion", false);
        diseno.put("artes", true);
        diseno.put("terceros", true);
        diseno.put("instalaciones", false);
        diseno.put("catalogos", true);
        diseno.put("reportes", true);
        diseno.put("configuracion", false);
        diseno.put("usuarios", false);
        diseno.put("roles", false);

        Map<String, Boolean> produccion =
                permisos.get(PRODUCCION);

        produccion.put("nueva_ot", false);
        produccion.put("buscar_ot", true);
        produccion.put("mis_ot", true);
        produccion.put("produccion", true);
        produccion.put("artes", true);
        produccion.put("terceros", true);
        produccion.put("instalaciones", false);
        produccion.put("catalogos", true);
        produccion.put("reportes", true);
        produccion.put("configuracion", false);
        produccion.put("usuarios", false);
        produccion.put("roles", false);

        Map<String, Boolean> instalaciones =
                permisos.get(INSTALACIONES);

        instalaciones.put("nueva_ot", false);
        instalaciones.put("buscar_ot", true);
        instalaciones.put("mis_ot", true);
        instalaciones.put("produccion", false);
        instalaciones.put("artes", false);
        instalaciones.put("terceros", true);
        instalaciones.put("instalaciones", true);
        instalaciones.put("catalogos", true);
        instalaciones.put("reportes", true);
        instalaciones.put("configuracion", false);
        instalaciones.put("usuarios", false);
        instalaciones.put("roles", false);

        Map<String, Boolean> consulta =
                permisos.get(CONSULTA);

        consulta.put("nueva_ot", false);
        consulta.put("buscar_ot", true);
        consulta.put("mis_ot", true);
        consulta.put("produccion", false);
        consulta.put("artes", false);
        consulta.put("terceros", false);
        consulta.put("instalaciones", false);
        consulta.put("catalogos", false);
        consulta.put("reportes", true);
        consulta.put("configuracion", false);
        consulta.put("usuarios", false);
        consulta.put("roles", false);
    }

    public static boolean tienePermiso(
            String rol,
            String permiso) {

        if (rol == null
                || permiso == null) {

            return false;
        }

        Map<String, Boolean> mapa =
                permisos.get(
                        rol.toUpperCase()
                );

        if (mapa == null) {

            return false;
        }

        return Boolean.TRUE.equals(
                mapa.get(permiso)
        );
    }

    public static Map<String, Boolean>
            getPermisos(String rol) {

        Map<String, Boolean> mapa =
                permisos.get(rol);

        if (mapa == null) {

            return new HashMap<>();
        }

        return new HashMap<>(
                mapa
        );
    }

    public static void guardarPermisos(
            String rol,
            Map<String, Boolean> nuevosPermisos) {

        if (rol == null
                || nuevosPermisos == null) {

            return;
        }

        permisos.put(
                rol,
                new HashMap<>(
                        nuevosPermisos
                )
        );
    }

    private Mod_RolesPermisos() {
    }

}//fin clase
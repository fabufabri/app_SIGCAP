// src/main/java/upse/SIGCAP/general/Mod_VariablesGlobales.java

package upse.SIGCAP.general;

public class Mod_VariablesGlobales {

    public static String g_nombreUsuario = "";

    public static int g_otSeleccionada = 0;

    public static String formatoFechaEsp = "dd/MM/yyyy";

    public static String formatoFechaIng = "yyyy/MM/dd";

    // DATOS DE LA EMPRESA

    public static String g_empresaNombre = "";

    public static String g_empresaRuc = "";

    public static String g_empresaDireccion = "";

    public static String g_empresaTelefono = "";

    public static String g_empresaCorreo = "";

    // CONFIGURACIÓN GENERAL

    public static String g_moneda = "USD";

    public static int g_diasAlertaOT = 2;

    public static int g_porcentajeCompletado = 100;

    // NUMERACIÓN DE OT

    public static String g_otPrefijo = "OT";

    public static int g_otAnio = java.time.LocalDate.now().getYear();

    public static int g_otSecuencia = 1;

    // NOTIFICACIONES

    public static boolean g_alertaOTVencida = true;

    public static boolean g_alertaOTProxima = true;

    public static boolean g_alertaArtePendiente = true;

    public static boolean g_alertaProduccionPendiente = true;

    public static boolean g_alertaInstalacionPendiente = true;

    public static boolean g_alertaOTCompletada = true;

    private Mod_VariablesGlobales() {
    }

}//fin clase
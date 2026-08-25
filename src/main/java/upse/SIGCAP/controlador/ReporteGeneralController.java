// src/main/java/upse/SIGCAP/controlador/ReporteGeneralController.java

package upse.SIGCAP.controlador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;
import static upse.SIGCAP.general.Mod_general.fun_mensajeInformacion;

public class ReporteGeneralController
        implements Initializable {

    @FXML
    private Button btn_actualizar;

    @FXML
    private Button btn_buscar;

    @FXML
    private Button btn_limpiar;

    @FXML
    private Button btn_imprimir;

    @FXML
    private Button btn_exportar;

    @FXML
    private Button btn_cerrar;

    private String tipoReporte = "";

    @Override
    public void initialize(
            URL url,
            ResourceBundle rb) {
    }

    public void setTipoReporte(
            String tipoReporte) {

        this.tipoReporte =
                tipoReporte == null
                        ? ""
                        : tipoReporte;

        configurarTitulo();
    }

    private void configurarTitulo() {

        if (tipoReporte.isEmpty()) {
            return;
        }

        /*
         * El ReporteGeneral.fxml ya existente
         * será utilizado para:
         *
         * PRODUCCION
         * ARTES
         * INSTALACIONES
         * TERCEROS
         * CLIENTES
         */
    }

    @FXML
    private void acc_actualizar() {

        fun_mensajeInformacion(
                "Reporte actualizado correctamente."
        );
    }

    @FXML
    private void acc_buscar() {

        fun_mensajeInformacion(
                "Ejecutando búsqueda del reporte."
        );
    }

    @FXML
    private void acc_limpiar() {

        fun_mensajeInformacion(
                "Filtros limpiados correctamente."
        );
    }

    @FXML
    private void acc_imprimir() {

        fun_mensajeInformacion(
                "Preparando reporte para impresión."
        );
    }

    @FXML
    private void acc_exportar() {

        fun_mensajeInformacion(
                "Preparando reporte para exportación."
        );
    }

    @FXML
    private void acc_cerrar() {

        if (btn_cerrar == null
                || btn_cerrar.getScene() == null) {

            return;
        }

        Stage stage =
                (Stage) btn_cerrar
                        .getScene()
                        .getWindow();

        stage.close();
    }

}//fin clase
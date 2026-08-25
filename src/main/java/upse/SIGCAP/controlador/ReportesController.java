// src/main/java/upse/SIGCAP/controlador/ReportesController.java

package upse.SIGCAP.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;

public class ReportesController implements Initializable {

    @FXML
    private Button btn_ot;

    @FXML
    private Button btn_produccion;

    @FXML
    private Button btn_artes;

    @FXML
    private Button btn_instalaciones;

    @FXML
    private Button btn_terceros;

    @FXML
    private Button btn_clientes;

    @FXML
    private Button btn_cerrar;

    @Override
    public void initialize(
            URL url,
            ResourceBundle rb) {
    }

    // =========================================================
    // ORDENES DE TRABAJO
    // =========================================================

    @FXML
    private void acc_ot() {

        abrirReporteOrdenes();
    }

    // =========================================================
    // PRODUCCION
    // =========================================================

    @FXML
    private void acc_produccion() {

        abrirReporteGeneral(
                "PRODUCCION"
        );
    }

    // =========================================================
    // ARTES
    // =========================================================

    @FXML
    private void acc_artes() {

        abrirReporteGeneral(
                "ARTES"
        );
    }

    // =========================================================
    // INSTALACIONES
    // =========================================================

    @FXML
    private void acc_instalaciones() {

        abrirReporteGeneral(
                "INSTALACIONES"
        );
    }

    // =========================================================
    // TERCEROS
    // =========================================================

    @FXML
    private void acc_terceros() {

        abrirReporteGeneral(
                "TERCEROS"
        );
    }

    // =========================================================
    // CLIENTES
    // =========================================================

    @FXML
    private void acc_clientes() {

        abrirReporteGeneral(
                "CLIENTES"
        );
    }

    // =========================================================
    // REPORTE ORDENES
    // =========================================================

    private void abrirReporteOrdenes() {

        try {

            URL recurso =
                    App.class.getResource(
                            "/upse/SIGCAP/vistas/ReporteOrdenes.fxml"
                    );

            if (recurso == null) {

                fun_mensajeError(
                        "No se encontró ReporteOrdenes.fxml."
                );

                return;
            }

            FXMLLoader loader =
                    new FXMLLoader(recurso);

            Parent root =
                    loader.load();

            Stage stage =
                    new Stage();

            stage.setTitle(
                    "SIGCAP - Reporte de Órdenes de Trabajo"
            );

            stage.initModality(
                    Modality.APPLICATION_MODAL
            );

            stage.initOwner(
                    btn_cerrar
                            .getScene()
                            .getWindow()
            );

            stage.setScene(
                    new Scene(root)
            );

            stage.setResizable(true);

            stage.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo abrir el Reporte de Órdenes de Trabajo.\n\n"
                    + e.getMessage()
            );
        }
    }

    // =========================================================
    // REPORTE GENERAL
    // =========================================================

    private void abrirReporteGeneral(
            String tipoReporte) {

        try {

            URL recurso =
                    App.class.getResource(
                            "/upse/SIGCAP/vistas/ReporteGeneral.fxml"
                    );

            if (recurso == null) {

                fun_mensajeError(
                        "No se encontró ReporteGeneral.fxml."
                );

                return;
            }

            FXMLLoader loader =
                    new FXMLLoader(recurso);

            Parent root =
                    loader.load();

            /*
             * ReporteGeneral.fxml ya es la vista creada
             * para los reportes generales.
             *
             * El controlador recibe el tipo de reporte
             * después de cargar el FXML.
             */

            Object controller =
                    loader.getController();

            if (controller
                    instanceof ReporteGeneralController) {

                ReporteGeneralController reporte =
                        (ReporteGeneralController) controller;

                reporte.setTipoReporte(
                        tipoReporte
                );
            }

            Stage stage =
                    new Stage();

            String titulo =
                    obtenerTitulo(
                            tipoReporte
                    );

            stage.setTitle(
                    "SIGCAP - " + titulo
            );

            stage.initModality(
                    Modality.APPLICATION_MODAL
            );

            stage.initOwner(
                    btn_cerrar
                            .getScene()
                            .getWindow()
            );

            stage.setScene(
                    new Scene(root)
            );

            stage.setResizable(true);

            stage.showAndWait();

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo abrir el reporte.\n\n"
                    + e.getMessage()
            );
        }
    }

    // =========================================================
    // TITULOS
    // =========================================================

    private String obtenerTitulo(
            String tipoReporte) {

        switch (tipoReporte) {

            case "PRODUCCION":
                return "Reporte de Producción";

            case "ARTES":
                return "Reporte de Artes";

            case "INSTALACIONES":
                return "Reporte de Instalaciones";

            case "TERCEROS":
                return "Reporte de Terceros";

            case "CLIENTES":
                return "Reporte de Clientes";

            default:
                return "Reporte General";
        }
    }

    // =========================================================
    // CERRAR
    // =========================================================

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
// src/main/java/upse/SIGCAP/controlador/CatalogosController.java

package upse.SIGCAP.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;

public class CatalogosController implements Initializable {

    @FXML
    private Label lbl_titulo;

    @FXML
    private Label lbl_subtitulo;

    @FXML
    private StackPane pnl_contenido;

    @Override
    public void initialize(
            URL url,
            ResourceBundle rb) {
    }

    @FXML
    private void acc_clientes() {

        abrirCatalogo(
                "Clientes.fxml",
                "Clientes",
                "Administración de clientes"
        );
    }

    @FXML
    private void acc_ciudades() {

        abrirCatalogo(
                "Ciudades.fxml",
                "Ciudades",
                "Administración de ciudades"
        );
    }

    @FXML
    private void acc_locales() {

        abrirCatalogo(
                "Locales.fxml",
                "Locales",
                "Administración de locales"
        );
    }

    @FXML
    private void acc_productos() {

        abrirCatalogo(
                "Productos.fxml",
                "Productos",
                "Administración de productos"
        );
    }

    @FXML
    private void acc_medidas() {

        abrirCatalogo(
                "Medidas.fxml",
                "Medidas",
                "Administración de medidas"
        );
    }

    @FXML
    private void acc_materiales() {

        abrirCatalogo(
                "Materiales.fxml",
                "Materiales",
                "Administración de materiales"
        );
    }

    @FXML
    private void acc_acabados() {

        abrirCatalogo(
                "Acabados.fxml",
                "Acabados",
                "Administración de acabados"
        );
    }

    @FXML
    private void acc_instalaciones() {

        abrirCatalogo(
                "TiposInstalacion.fxml",
                "Tipos de Instalación",
                "Administración de tipos de instalación"
        );
    }

    @FXML
    private void acc_terceros() {

        abrirCatalogo(
                "Terceros.fxml",
                "Terceros",
                "Administración de terceros"
        );
    }

    @FXML
    private void acc_estados() {

        abrirCatalogo(
                "EstadosProceso.fxml",
                "Estados de Proceso",
                "Administración de estados de proceso"
        );
    }

    private void abrirCatalogo(
            String archivo,
            String titulo,
            String subtitulo) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            App.class.getResource(
                                    "/upse/SIGCAP/vistas/"
                                    + archivo
                            )
                    );

            Parent root =
                    loader.load();

            lbl_titulo.setText(
                    titulo
            );

            lbl_subtitulo.setText(
                    subtitulo
            );

            pnl_contenido
                    .getChildren()
                    .clear();

            pnl_contenido
                    .getChildren()
                    .add(root);

        } catch (IOException e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo cargar el catálogo "
                    + titulo
                    + ".\n\n"
                    + e.getMessage()
            );
        }
    }

    @FXML
    private void acc_volver() {

        try {

            App.setRoot(
                    "Dashboard"
            );

        } catch (Exception e) {

            e.printStackTrace();

            fun_mensajeError(
                    "No se pudo regresar al Dashboard.\n\n"
                    + e.getMessage()
            );
        }
    }

}//fin clase
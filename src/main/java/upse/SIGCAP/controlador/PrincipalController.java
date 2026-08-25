// src/main/java/upse/SIGCAP/controlador/PrincipalController.java

package upse.SIGCAP.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import static upse.SIGCAP.general.Mod_general.fun_mensajeError;

public class PrincipalController implements Initializable {

    @FXML
    private Button btn_proformas;

    @FXML
    private Button btn_facturacion;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    @FXML
    private void acc_proformas() {

        abrirVentana(
                "Proformas.fxml",
                "Proformas"
        );
    }

    @FXML
    private void acc_facturacion() {

        abrirVentana(
                "Facturas.fxml",
                "Facturación"
        );
    }

    private void abrirVentana(
            String archivo,
            String titulo) {

        try {

            FXMLLoader loader =
                    new FXMLLoader(
                            getClass().getResource(
                                    "/upse/SIGCAP/vistas/"
                                    + archivo
                            )
                    );

            Parent root = loader.load();

            Stage stage = new Stage();

            stage.setTitle(titulo);

            stage.setScene(
                    new Scene(root)
            );

            stage.show();

        } catch (IOException e) {

            fun_mensajeError(
                    "No se pudo abrir "
                    + titulo
                    + ".\n"
                    + e.getMessage()
            );
        }
    }

}//fin clase
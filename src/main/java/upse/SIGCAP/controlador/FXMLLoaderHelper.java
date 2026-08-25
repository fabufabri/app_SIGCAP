// src/main/java/upse/SIGCAP/controlador/FXMLLoaderHelper.java
package upse.SIGCAP.controlador;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FXMLLoaderHelper {

    private FXMLLoaderHelper() {
    }

    public static void abrirFormularioOT(int id)
            throws IOException {

        FXMLLoader loader =
                new FXMLLoader(
                        App.class.getResource(
                                "/upse/SIGCAP/vistas/"
                                + "NuevaOrdenTrabajo.fxml"
                        )
                );

        Parent root =
                loader.load();

        NuevaOrdenTrabajoController controller =
                loader.getController();

        controller.setOtId(id);

        Stage stage =
                new Stage();

        stage.initModality(
                Modality.APPLICATION_MODAL
        );

        stage.initOwner(
                App.getStagePrincipal()
        );

        stage.setTitle(
                id == 0
                        ? "Nueva Orden de Trabajo"
                        : "Editar Orden de Trabajo"
        );

        stage.setScene(
                new Scene(root)
        );

        stage.setResizable(true);

        stage.showAndWait();
    }

}//fin clase